package github.zero.miaosha.vo;

import github.zero.miaosha.domain.Goods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-14-15:21
 */
@Setter
@Getter
@ToString
public class GoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
