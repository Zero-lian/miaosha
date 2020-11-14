package github.zero.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-12-10:17
 */
public class MD5Util {

    private static final String salt = "1a2b3c4d";

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }
    //第一次md5加密，使用固定salt
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }
    //第二次md5加密，使用随机salt
    public static String formPassToDBPass(String formPass ,String saltDB){
        String str = "" + saltDB.charAt(0) + saltDB.charAt(2) + formPass + saltDB.charAt(5) + saltDB.charAt(4);
        return md5(str);
    }
    //两次加密
    public static String inputPassToDBPass(String formPass ,String saltDB){
        return formPassToDBPass(inputPassToFormPass(formPass),saltDB);
    }

    //test
    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }
}
