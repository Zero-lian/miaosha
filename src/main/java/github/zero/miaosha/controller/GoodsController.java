package github.zero.miaosha.controller;

import github.zero.miaosha.domain.MiaoshaUser;
import github.zero.miaosha.redis.RedisService;
import github.zero.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/to_list")
    public String list(Model model , MiaoshaUser user){
        model.addAttribute("user",user);
        return "goods_list";
    }
}
