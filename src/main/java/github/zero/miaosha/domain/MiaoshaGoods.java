package github.zero.miaosha.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-14-15:13
 */
@Setter
@Getter
public class MiaoshaGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
