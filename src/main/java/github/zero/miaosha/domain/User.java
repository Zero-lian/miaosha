package github.zero.miaosha.domain;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-11-9:47
 */
public class User {
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
