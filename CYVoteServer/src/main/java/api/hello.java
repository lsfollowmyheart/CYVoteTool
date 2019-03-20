package api;

import common.CommonUtil;
import domain.Hello;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class hello {
    @RequestMapping(value = "/CYVoteServer/hello", method = RequestMethod.POST)
    public Hello hello(@RequestBody Hello hi) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hello", "haha");
        CommonUtil.transMap2Bean2(map, hi);
        String hiStr = hi.getHello();
        Hello hello = new Hello();
        System.out.println(hiStr);
        hello.setHello(hiStr);
        return hello;
    }
}
