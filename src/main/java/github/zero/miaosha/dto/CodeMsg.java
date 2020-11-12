package github.zero.miaosha.dto;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-10-18:16
 */
public class CodeMsg {
    private int code;
    private String message;

    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常");
    //登陆模块 5002XX

    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX
    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
