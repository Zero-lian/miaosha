package github.zero.miaosha.validator;

import github.zero.miaosha.domain.MiaoshaUser;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-20-23:33
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user){
        userHolder.set(user);
    }

    public static MiaoshaUser getUser(){
        return userHolder.get();
    }
}
