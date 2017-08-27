package com.hstba.jingzheng;

import com.hstba.jingzheng.service.WechatApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JingzhengApplicationTests {
    @Autowired
    private WechatApiService wechatApiService;

    @Test
    public void contextLoads() {
        String tonken = wechatApiService.getAccessToken();
        System.out.println(tonken);
//        wechatApiService.senTplMsg(tonken, "okwgf0Xd79QuZ-31WcsLOLTeJMqM", "0c8ce811d7ebd7478b3c4fe693a18203");
    }

}
