package com.hstba.jingzheng.task;

import com.hstba.jingzheng.Application;
import com.hstba.jingzheng.entity.DetectLog;
import com.hstba.jingzheng.entity.User;
import com.hstba.jingzheng.mapper.UserMapper;
import com.hstba.jingzheng.service.DetectLogService;
import com.hstba.jingzheng.service.RedisService;
import com.hstba.jingzheng.service.WechatApiService;
import com.hstba.jingzheng.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DetectServiceTasks {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DetectLogService detectLogService;
    @Autowired
    private WechatApiService wechatApiService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;


    @Scheduled(fixedRate = 60000)
    public void timerRate() {
        String url = "https://api.jinjingzheng.zhongchebaolian.com/enterbj/jsp/enterbj/addcartype.jsp";
        ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
        int results = 0;
        if (entity.getStatusCodeValue() == 200) {
            String body = entity.getBody();
            if (body.indexOf("排队人数过多") < 0) {
                results = 1;
                redisService.set("lastOpenTime", new Date().getTime() + "");
                List<User> users = userMapper.getAllRemindUser();
                users.forEach(u -> {
                    userMapper.setRemind(0, u.getOpenid(), "");
                    String token = wechatApiService.getAccessToken();
                    wechatApiService.senTplMsg(token, u.getOpenid(), u.getFormId());
                });
            }
        }
        redisService.set("currentState", results + "");
        detectLogService.saveDetectLog(new DetectLog().setResults(results));
    }
}