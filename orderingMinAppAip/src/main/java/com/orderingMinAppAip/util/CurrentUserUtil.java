package com.blogs.util;

import com.blogs.constants.JwtConstants;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class CurrentUserUtil {

    public static Integer getUserId() throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String jwtToken = CookieUtil.getValue(request, JwtConstants.COOKIE_TOKEN);

        if (jwtToken == null){
            return null;
        }
        if(jwtToken.isEmpty()){
            return null;
        }
        return JWTUtil.getUserId(jwtToken);
    }
}
