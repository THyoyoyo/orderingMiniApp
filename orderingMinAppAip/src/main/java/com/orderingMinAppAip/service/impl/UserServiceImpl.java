package com.orderingMinAppAip.service.impl;

import com.orderingMinAppAip.mapper.userInfo.UserInfoMapper;
import com.orderingMinAppAip.model.userInfo.UserInfo;
import com.orderingMinAppAip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoById(Integer userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        return userInfo;
    }
}
