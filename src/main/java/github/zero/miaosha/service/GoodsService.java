package github.zero.miaosha.service;

import github.zero.miaosha.dao.GoodsDao;
import github.zero.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-14-15:19
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.lisrGoodsVo();
    }
}
