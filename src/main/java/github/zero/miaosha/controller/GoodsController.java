package github.zero.miaosha.controller;

import github.zero.miaosha.domain.MiaoshaUser;
import github.zero.miaosha.redis.RedisService;
import github.zero.miaosha.service.GoodsService;
import github.zero.miaosha.service.MiaoshaUserService;
import github.zero.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-13-22:10
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/to_list")
    public String list(Model model , MiaoshaUser user){
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
        return "goods_list";
    }
}
