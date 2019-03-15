package api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {
    @RequestMapping(value = "/CYVoteServer/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }
}
