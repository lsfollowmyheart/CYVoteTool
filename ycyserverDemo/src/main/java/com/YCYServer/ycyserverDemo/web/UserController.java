package com.YCYServer.ycyserverDemo.web;

import com.YCYServer.ycyserverDemo.entity.User;
import com.YCYServer.ycyserverDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/superadmin")
public class UserController {
    
    @Autowired
    private UserService userService;

    @RequestMapping(value="/listuser", method=RequestMethod.GET)
    private Map<String, Object> listUser() {
        Map<String, Object> modelMap = new HashMap<>();
        List<User> list = userService.getUserList();
        modelMap.put("areaList", list);
        return modelMap;
    }

    @RequestMapping(value="/userbyid", method=RequestMethod.GET)
    private Map<String, Object> userById(Integer id) {
        Map<String, Object> modelMap = new HashMap<>();
        User user = userService.getUserById(id);
        modelMap.put("userById", user);
        return modelMap;
    }

    @RequestMapping(value="/adduser", method=RequestMethod.POST)
    private Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.addUser(user));
        return modelMap;
    }

    @RequestMapping(value="/modifyuser", method=RequestMethod.POST)
    private Map<String, Object> modifyUser(@RequestBody User user) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.modifyUser(user));
        return modelMap;
    }

    @RequestMapping(value="/removeuser", method=RequestMethod.GET)
    private Map<String, Object> removeUser(@RequestBody Integer id) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.deleteUser(id));
        return modelMap;
    }
}
