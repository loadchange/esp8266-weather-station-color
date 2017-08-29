package com.hstba.jingzheng;

import com.hstba.jingzheng.entity.User;
import com.hstba.jingzheng.mapper.UserMapper;
import com.hstba.jingzheng.service.RedisService;
import com.hstba.jingzheng.service.WechatApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JingzhengApplicationTests {
    @Autowired
    private WechatApiService wechatApiService;
    @Autowired
    private RedisService redisService;

    @Test
    public void contextLoads() {
//        String tonken = wechatApiService.getAccessToken(false);
//        wechatApiService.senTplMsg(tonken, "okwgf0Xd79QuZ-31WcsLOLTeJMqM", "aa23b3c6c823a391a12fd20391075c0d");
    }

}
