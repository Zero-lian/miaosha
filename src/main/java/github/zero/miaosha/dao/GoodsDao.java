package github.zero.miaosha.dao;

import github.zero.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-14-15:20
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price " +
            "from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> lisrGoodsVo();
}
