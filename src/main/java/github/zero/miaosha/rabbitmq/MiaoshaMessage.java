package github.zero.miaosha.rabbitmq;

import github.zero.miaosha.domain.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-18-21:37
 */
@Setter
@Getter
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;
}
