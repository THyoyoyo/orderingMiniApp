package com.orderingMinAppAip.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.orderingMinAppAip.constants.JwtConstants;
import com.orderingMinAppAip.dto.UserLoginDto;
import com.orderingMinAppAip.dto.user.UserRegisterDto;
import com.orderingMinAppAip.exception.authorityException;
import com.orderingMinAppAip.mapper.userInfo.UserInfoMapper;
import com.orderingMinAppAip.model.userInfo.UserInfo;
import com.orderingMinAppAip.util.CookieUtil;
import com.orderingMinAppAip.util.CurrentUserUtil;
import com.orderingMinAppAip.util.IPUtil;
import com.orderingMinAppAip.util.JWTUtil;
import com.orderingMinAppAip.vo.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@Api(tags = "用户登录注册")
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    UserInfoMapper userInfoMapper;

    @PostMapping("/userLogin")
    @ApiOperation("用户登录")
    public R userLogin(@RequestBody UserLoginDto dto, HttpServletRequest request, HttpServletResponse response) {
        String ip = IPUtil.getIpAddr(request);
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();

        userInfoQueryWrapper.eq("account",dto.getAccount()).eq("password",dto.getPassword());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoQueryWrapper);
        if(userInfo == null){
            return R.failed(404,"帐号或密码错误");
        }


        //判断是否更新token
        if (!JWTUtil.verify(userInfo.getToken())){
            String token = JWTUtil.generateToken(userInfo.getName(),userInfo.getId(), ip);
            userInfo.setToken(token);
        }

        userInfo.setUpTime(new Date());

        //客户端添加Cookie
        CookieUtil.set(response, JwtConstants.COOKIE_TOKEN, userInfo.getToken(), JwtConstants.TOKEN_EXPIRE_TIME.intValue());

        userInfoMapper.updateById(userInfo);

        return R.succeed(userInfo);
    }


    @PostMapping("/userRegister")
    @ApiOperation("用户注册")
    public R userRegister( @RequestBody UserRegisterDto dto){
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(dto,userInfo);
        userInfo.setCreatorTime(new Date());
        userInfoMapper.insert(userInfo);
        return R.succeed();
    }

    @GetMapping("/userOut")
    @ApiOperation("用户退出")
    public R userOut (HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer userId = CurrentUserUtil.getUserId();
        if(userId !=null){
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userId);
            userInfo.setToken("0");
            userInfoMapper.updateById(userInfo);
        }
        CookieUtil.set(response, JwtConstants.COOKIE_TOKEN, null,1);
        return  R.succeed(userId);
    }

}
