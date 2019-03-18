package com.YCYServer.ycyserverDemo.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getUserList() {
        System.out.println(userService);
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void addUser() {
    }

    @Test
    public void modifyUser() {
    }

    @Test
    public void deleteUser() {
    }
}