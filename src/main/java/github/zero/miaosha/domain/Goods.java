package github.zero.miaosha.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-14-15:11
 */
@Getter
@Setter
public class Goods {
    private Long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;
}
