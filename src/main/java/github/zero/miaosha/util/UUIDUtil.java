package github.zero.miaosha.util;

import java.util.UUID;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-13-20:50
 */
public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
