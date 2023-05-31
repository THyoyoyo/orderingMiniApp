package com.orderingMinAppAip.service;

import com.orderingMinAppAip.model.userInfo.UserInfo;
import org.apache.catalina.User;

public interface UserService {
    /**
     * 根据用户ID查询用户信息
     * */
    UserInfo getUserInfoById(Integer userId);
}
