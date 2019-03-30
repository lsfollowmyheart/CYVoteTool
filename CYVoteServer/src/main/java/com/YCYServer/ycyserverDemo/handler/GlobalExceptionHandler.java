package com.YCYServer.ycyserverDemo.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=RuntimeException.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest request, RuntimeException e) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", false);
        modelMap.put("errMsg", "Exception Handler: RuntimeException " + e.getMessage());
        return modelMap;
    }
    private Map<String, Object> exceptionHandler(HttpServletRequest request, ArithmeticException e) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("success", false);
        modelMap.put("errMsg", "ArithmeticException " + e.getMessage());
        return modelMap;
    }
}
