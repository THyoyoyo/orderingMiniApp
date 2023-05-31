package com.orderingMinAppAip.controller;


import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.dto.user.UpUserInfoDto;
import com.orderingMinAppAip.model.userInfo.UserInfo;
import com.orderingMinAppAip.service.UserService;
import com.orderingMinAppAip.util.CurrentUserUtil;
import com.orderingMinAppAip.vo.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/upInfo")
    @ApiOperation(value = "更新用戶信息")
    @Token
    public R upInfo(@RequestBody UpUserInfoDto dto) throws Exception {

        Integer userId = CurrentUserUtil.getUserId();
        UserInfo userInfo = userService.getUserInfoById(userId);
        BeanUtils.copyProperties(dto,userInfo);
        userService.upUserInfo(userInfo);
        return R.succeed();
    }


    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息")
    @Token
    public R getUserInfo() throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        UserInfo userInfo = userService.getUserInfoById(userId);
        return R.succeed(userInfo);
    }
}
