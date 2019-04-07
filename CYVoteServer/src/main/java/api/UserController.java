package api;

import dao.domain.User;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping(value = "/test")
    private String test(){
        return "test";
    }

    @GetMapping(value = "/login")
    private String login() {
        return "Login";
    }

    @PostMapping(value = "/login")
    private Map<String, Object> login(@RequestBody User user) {
//        System.out.println("User: " + user);
        Map<String, Object> modelMap = new HashMap<>();
        if (user.getUsername().isEmpty()) {
            modelMap.put("msg", "Error: Username cannot be empty");
            return modelMap;
        }
        if (user.getPassword().isEmpty()) {
            modelMap.put("msg", "Error: Password cannot be empty");
            return modelMap;
        }
        modelMap.put("success", userService.login(user));
        return modelMap;
    }

    @GetMapping(value="/users")
    private Map<String, Object> listUser() {
        Map<String, Object> modelMap = new HashMap<>();
        List<User> list = userService.getUserList();
        modelMap.put("userList", list);
        return modelMap;
    }

    @GetMapping(value="/users/{id}")
    private Map<String, Object> userById(@PathVariable Integer id) {
        Map<String, Object> modelMap = new HashMap<>();
        User user = userService.getUserById(id);
        modelMap.put("user", user);
        return modelMap;
    }

    @PostMapping(value="/users")
    private Map<String, Object> addUser(@RequestBody User user) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.addUser(user));
        return modelMap;
    }

    @PutMapping(value="/users")
    private Map<String, Object> modifyUser(@RequestBody User user) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.modifyUser(user));
        return modelMap;
    }

    @DeleteMapping(value="/users/{id}")
    private Map<String, Object> removeUser(@PathVariable Integer id) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", userService.deleteUser(id));
        return modelMap;
    }
}
