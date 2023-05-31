package com.orderingMinAppAip.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orderingMinAppAip.model.userInfo.UserInfo;

public interface UserService{
    /**
     * 根据用户ID查询用户信息
     * */
    UserInfo getUserInfoById(Integer userId);

    /**
     * 更新用户信息
     * */
    void upUserInfo(UserInfo userInfo);
}
