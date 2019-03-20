package com.YCYServer.ycyserverDemo.service.impl;

import com.YCYServer.ycyserverDemo.service.UserService;
import com.YCYServer.ycyserverDemo.dao.UserDao;
import com.YCYServer.ycyserverDemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUserList() {
        return userDao.queryUser();
    }

    @Override
    public User getUserById(int id) {
        return userDao.queryUserById(id);
    }

    @Transactional  // rolls back RuntimeException by default
    @Override
    public boolean addUser(User user) {
        if (user.getUsername() != null && !user.getUsername().equals("")) {
            try {
                int effectedNum = userDao.insertUser(user);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("Failed to add user");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("Error: Failed to add user: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Error: Username cannot be empty.");
        }
    }

    @Transactional
    @Override
    public boolean modifyUser(User user) {
        if (user.getUsername() != null && !user.getUsername().equals("")) {
            try {
                int effectedNum = userDao.updateUser(user);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("Failed to update user");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("Error: Failed to update user: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Error: Username cannot be empty.");
        }
    }

    @Transactional
    @Override
    public boolean deleteUser(int id) {
        if (id > 0) {
            try {
                int effectedNum = userDao.deleteUser(id);
                if (effectedNum > 0) {
                    return true;
                } else {
                    throw new RuntimeException("Failed to delete user");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException("Error: Failed to delete user: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("Error: user id cannot be empty.");
        }
    }
}
