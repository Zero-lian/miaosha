package github.zero.miaosha.service;

import github.zero.miaosha.dao.UserDao;
import github.zero.miaosha.domain.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Desciption
 * @Author yucanlian
 * @Date 2020-11-11-9:50
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx(){
        User user = new User();
        user.setId(2);
        user.setName("ycl");
        userDao.insert(user);

        User u2 = new User();
        u2.setId(1);
        u2.setName("ycl");
        userDao.insert(u2);
        return true;
    }

}
