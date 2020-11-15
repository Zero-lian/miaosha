package github.zero.miaosha.service;

import github.zero.miaosha.domain.MiaoshaUser;
import github.zero.miaosha.domain.OrderInfo;
import github.zero.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-15-11:16
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存
        goodsService.reduceStock(goods);
        //创建订单
        return orderService.createOrder(user,goods);
    }
}
