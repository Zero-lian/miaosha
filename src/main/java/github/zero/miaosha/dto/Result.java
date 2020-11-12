package github.zero.miaosha.dto;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-10-18:12
 */
public class Result<T> {
    private int code;
    private String message;
    private T date;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getDate() {
        return date;
    }

    private Result(T data) {
        this.code = 0;
        this.message = "success";
        this.date = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null){
            return;
        }
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMessage();
    }

    /**
     * 返回成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result<T>(data);
    }

    /**
     * 返回失败
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }
}
