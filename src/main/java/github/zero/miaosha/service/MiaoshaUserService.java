package github.zero.miaosha.service;


import github.zero.miaosha.dao.MiaoshaUserDao;
import github.zero.miaosha.domain.MiaoshaUser;
import github.zero.miaosha.dto.CodeMsg;
import github.zero.miaosha.exception.GlobalException;
import github.zero.miaosha.redis.MiaoshaUserKey;
import github.zero.miaosha.redis.RedisService;
import github.zero.miaosha.util.MD5Util;
import github.zero.miaosha.util.UUIDUtil;
import github.zero.miaosha.vo.LoginVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-13-10:46
 */
@Service
public class MiaoshaUserService {

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public static final String COOKI_NAME_TOKEN = "token";

    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        //将 token 和与之对应的 user 写入到 redis
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        //设置过期时间
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }
}
