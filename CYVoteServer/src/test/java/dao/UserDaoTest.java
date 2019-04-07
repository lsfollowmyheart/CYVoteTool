package dao;


import dao.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void queryUser(){
        List<User> userList = userDao.queryUser();
        assertEquals(8, userList.size());
    }
    @Test
    public void queryUserById(){
        int id = 1;
        User user = userDao.queryUserById(id);
        assertEquals("a", user.getUsername());
    }
    @Test
    public void insertUser(){
        User user = new User();
        user.setUsername("we");
        user.setPassword("we");
        int effectedNum = userDao.insertUser(user);
        assertEquals(1, effectedNum);

    }
    @Test
    public void updateUser(){
        User user = new User();
        user.setId(11);
        user.setUsername("Ron");
        user.setPassword("1234");
        int num = userDao.updateUser(user);
        assertEquals(1, num);
    }
    @Test
    public void deleteUser(){
        int num = userDao.deleteUser(11);
        assertEquals(1, num);
    }

}