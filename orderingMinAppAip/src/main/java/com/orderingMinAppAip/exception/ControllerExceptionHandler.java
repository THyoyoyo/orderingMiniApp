package com.orderingMinAppAip.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 权限检验
     * */
    @ExceptionHandler(authorityException.class)
    @ResponseBody
    public Map<String, Object> authorityHandler(authorityException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", ex.getCode());
        map.put("message", ex.getMessage());
        return map;
    }
}
