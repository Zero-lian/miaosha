package github.zero.miaosha.redis;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-11-22:52
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
