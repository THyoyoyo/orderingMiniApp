package com.orderingMinAppAip.controller;


import com.orderingMinAppAip.annotation.Token;
import com.orderingMinAppAip.mapper.test.TestMapper;
import com.orderingMinAppAip.model.test.Test;
import com.orderingMinAppAip.vo.common.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "测试接口")
@RequestMapping("/api/test")
public class TestController {


    @Autowired
    TestMapper testMapper;

    @PostMapping("/addTest")
    @ApiOperation("测试添加")
    public R addTest(@RequestBody Map<String,Object> dto){
        String name = (String) dto.get("name");
        Test test = new Test();
        test.setName(name);
        int insert = testMapper.insert(test);
        return  R.succeed(insert);
    }

    @GetMapping("/getTest")
    @ApiOperation("测试获取")
    @Token
    public R getTest(){
        List<Test> tests = testMapper.selectList(null);
        return R.succeed(tests);
    }
}
