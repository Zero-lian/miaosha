package github.zero.miaosha.exception;

import github.zero.miaosha.dto.CodeMsg;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-13-18:54
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg(){
        return codeMsg;
    }
}
