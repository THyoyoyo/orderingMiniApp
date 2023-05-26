package com.orderingMinAppAip.config;

import com.orderingMinAppAip.interceptor.LoginInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }



    @Bean
    public LoginInterceptor getLoginInterceptor () {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        return new LoginInterceptor();
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLoginInterceptor())
                //所有请求都被拦截包括静态资源
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
}
