package github.zero.miaosha.controller;

import com.sun.org.apache.bcel.internal.classfile.Code;
import github.zero.miaosha.domain.MiaoshaOrder;
import github.zero.miaosha.domain.MiaoshaUser;
import github.zero.miaosha.domain.OrderInfo;
import github.zero.miaosha.rabbitmq.MQSender;
import github.zero.miaosha.rabbitmq.MiaoshaMessage;
import github.zero.miaosha.redis.*;
import github.zero.miaosha.result.CodeMsg;
import github.zero.miaosha.result.Result;
import github.zero.miaosha.service.GoodsService;
import github.zero.miaosha.service.MiaoshaService;
import github.zero.miaosha.service.MiaoshaUserService;
import github.zero.miaosha.service.OrderService;
import github.zero.miaosha.validator.AccessLimit;
import github.zero.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

	@Autowired
    MiaoshaUserService userService;
	
	@Autowired
    RedisService redisService;
	
	@Autowired
    GoodsService goodsService;
	
	@Autowired
    OrderService orderService;
	
	@Autowired
    MiaoshaService miaoshaService;

	@Autowired
	MQSender sender;
	private Map<Long,Boolean> localOverMap = new HashMap<Long, Boolean>();

	/**
	 * 系统初始化
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		if (goodsList == null){
			return;
		}
		for (GoodsVo goods : goodsList) {
			redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goods.getId(),goods.getStockCount());
			localOverMap.put(goods.getId(),false);
		}
	}

	//环境还原，用于压测
	@RequestMapping(value = "/reset",method = RequestMethod.GET)
	@ResponseBody
	public Result<Boolean> reset(Model model){
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		for (GoodsVo goods : goodsList) {
			goods.setStockCount(10);
			redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goods.getId(),10);
			localOverMap.put(goods.getId(),false);
		}
		redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
		redisService.delete(MiaoshaKey.isGoodsOver);
		miaoshaService.reset(goodsList);
		return Result.success(true);
	}
	
	/**
	 * QPS:1306
	 * 5000 * 10
	 * */
    @RequestMapping(value="/do_miaosha", method=RequestMethod.POST)
    @ResponseBody
    public Result<Integer> miaosha(Model model, MiaoshaUser user,
                                     @RequestParam("goodsId")long goodsId) {
    	model.addAttribute("user", user);
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	//内存标记，减少redis访问
		boolean over = localOverMap.get(goodsId);
    	if (over){
    		return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
    	//预减库存
		Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
    	if (stock < 0){
    		localOverMap.put(goodsId,true);
    		return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		//判断是否已经秒杀到了
		MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
		if(order != null) {
			return Result.error(CodeMsg.REPEATE_MIAOSHA);
		}
		//入队
		MiaoshaMessage mm = new MiaoshaMessage();
		mm.setUser(user);
		mm.setGoodsId(goodsId);
		sender.sendMiaoshaMessage(mm);
		return Result.success(0);//排队中
    	/*//判断库存
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);//10个商品，req1 req2
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		return Result.error(CodeMsg.MIAO_SHA_OVER);
    	}
    	//判断是否已经秒杀到了
    	MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		return Result.error(CodeMsg.REPEATE_MIAOSHA);
    	}
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = miaoshaService.miaosha(user, goods);
        return Result.success(orderInfo);*/
    }

	/**
	 *秒杀结果
	 * @param model
	 * @param user
	 * @param goodsId
	 * @return 成功返回orderId,-1表示库存不足，0表示在排队
	 */
	@RequestMapping(value="/result", method=RequestMethod.GET)
	@ResponseBody
	public Result<Long> miaoshaResult(Model model, MiaoshaUser user,
								   @RequestParam("goodsId")long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		long result = miaoshaService.getMiaoshaResult(user.getId(),goodsId);
		return Result.success(result);
	}

	@AccessLimit(seconds=10,maxCount=5,needLogin=true)
	@RequestMapping(value="/path", method=RequestMethod.GET)
	@ResponseBody
	public Result<String> getMiaoshaPath(HttpServletRequest request,
										 Model model, MiaoshaUser user,
										 @RequestParam("goodsId")long goodsId,
										 @RequestParam(value = "verifyCode",defaultValue = "0")int verifyCode) {
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}

		//验证码校验
		boolean check = miaoshaService.checkVerifyCode(user,goodsId,verifyCode);
		if (!check){
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
		}
		String path = miaoshaService.createMiaoshaPath(user,goodsId);
		return Result.success(path);
	}

	@RequestMapping(value="/verifyCode", method=RequestMethod.GET)
	@ResponseBody
	public Result<String> getMiaoshaVerifyCode(HttpServletResponse response,Model model, MiaoshaUser user,
											   @RequestParam("goodsId")long goodsId) {
		model.addAttribute("user", user);
		if (user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		BufferedImage image = miaoshaService.createVerifyCode(user,goodsId);
		try {
			OutputStream out = response.getOutputStream();
			ImageIO.write(image,"JPEG",out);
			out.flush();
			out.close();
			return null;
		}catch (Exception e){
			return Result.error(CodeMsg.MIAOSHA_FAIL);
		}
	}

}
