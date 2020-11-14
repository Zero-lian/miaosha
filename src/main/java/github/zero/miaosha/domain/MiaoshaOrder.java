package github.zero.miaosha.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-14-15:15
 */
@Setter
@Getter
public class MiaoshaOrder {
    private Long id;
    private Long userId;
    private Long  orderId;
    private Long goodsId;
}
