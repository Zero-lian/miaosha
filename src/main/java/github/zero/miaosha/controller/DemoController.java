package github.zero.miaosha.controller;

import github.zero.miaosha.domain.User;
import github.zero.miaosha.dto.CodeMsg;
import github.zero.miaosha.dto.Result;
import github.zero.miaosha.redis.RedisService;
import github.zero.miaosha.redis.UserKey;
import github.zero.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-10-17:58
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @GetMapping("/hello")
    public Result<String> home(){
        return Result.success("hello wrold");
    }

    @GetMapping("/error")
    public Result<String> demoError(){
        return Result.error(CodeMsg.SERVER_ERROR);
    }

    @GetMapping("/dbget")
    @ResponseBody
    public Result<User> dbget(){
        User user = userService.getById(1);
        return Result.success(user);
    }
    @GetMapping("/tx")
    @ResponseBody
    public Result<Boolean> tx(){
        userService.tx();
        return Result.success(true);
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,""+1, User.class);
        return Result.success(user);
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("ycl");
        redisService.set(UserKey.getById,""+1,user);//UserKey:id1
        return Result.success(true);
    }
}
