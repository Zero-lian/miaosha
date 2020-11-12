package github.zero.miaosha.redis;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-11-22:58
 */
public class OrderKey extends BasePrefix {


    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
