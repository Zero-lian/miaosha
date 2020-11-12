package github.zero.miaosha.redis;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-11-22:57
 */
public class UserKey extends BasePrefix {


    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");

}
