package com.orderingMinAppAip.interceptor;


import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.exception.authorityException;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Token annotation;
        // 如果不是映射到方法直接通过
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            annotation = handlerMethod.getMethod().getAnnotation(Token.class);

            //没有声明需要权限,或者声明不验证权限
            if(annotation == null || annotation.validate() == false){
                return true;
            }else {
                throw new authorityException(404,"权限不足,请通知管理员THyo");
            }
        }
        return true;
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}


