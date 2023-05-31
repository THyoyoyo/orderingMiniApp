package com.orderingMinAppAip.interceptor;


import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.exception.authorityException;
import com.orderingMinAppAip.mapper.userInfo.UserInfoMapper;
import com.orderingMinAppAip.model.userInfo.UserInfo;
import com.orderingMinAppAip.service.UserService;
import com.orderingMinAppAip.util.CookieUtil;
import com.orderingMinAppAip.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    UserService userService;

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
                String token = CookieUtil.getValue(request, "cookieToken");

                if (!JWTUtil.verify(token)){
                    throw new authorityException(402,"登录信息已失效，请重新登录");
                }

                Integer userId = JWTUtil.getUserId(token);
                UserInfo userInfo= userService.getUserInfoById(userId);

                if(userInfo == null){
                    throw new authorityException(402,"用户信息已失效，请重新登录");
                } else if(!token.equals(userInfo.getToken())){
                    throw new authorityException(402,"token无效，请重新登录");
                }
            }
        }
        return true;
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}


