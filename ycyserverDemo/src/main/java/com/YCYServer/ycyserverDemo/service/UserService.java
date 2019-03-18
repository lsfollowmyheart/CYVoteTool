package com.YCYServer.ycyserverDemo.service;

import com.YCYServer.ycyserverDemo.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUserList();
    User getUserById(int id);
    boolean addUser(User user);
    boolean modifyUser(User user);
    boolean deleteUser(int id);
}
