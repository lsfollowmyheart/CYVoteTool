package ycy.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloYCY {
    @RequestMapping(value="/hello", method=RequestMethod.GET)
    public String hello(){
        return "Hello YCY";
    }
}
