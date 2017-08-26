package com.hstba.jingzheng.task;

import com.hstba.jingzheng.entity.DetectLog;
import com.hstba.jingzheng.service.DetectLogService;
import com.hstba.jingzheng.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class DetectServiceTasks {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DetectLogService detectLogService;
    @Autowired
    private RedisService redisService;

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
            }
        }
        redisService.set("currentState", results + "");
        detectLogService.saveDetectLog(new DetectLog().setResults(results));
    }
}