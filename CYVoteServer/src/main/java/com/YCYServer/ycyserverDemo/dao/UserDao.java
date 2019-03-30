package com.YCYServer.ycyserverDemo.dao;


import com.YCYServer.ycyserverDemo.entity.User;

import java.util.List;

public interface UserDao {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = "username, password";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    List<User> queryUser();
    User queryUserById(int id);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int id);

    User login(User user);

}
