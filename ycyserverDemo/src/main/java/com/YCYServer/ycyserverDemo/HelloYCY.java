package com.YCYServer.ycyserverDemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloYCY {
    @RequestMapping("/hello")
    public String what(){
        return "Hello YCY";
    }
}
