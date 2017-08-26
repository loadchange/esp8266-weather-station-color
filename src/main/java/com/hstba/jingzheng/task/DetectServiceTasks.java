package com.hstba.jingzheng.task;

import com.hstba.jingzheng.entity.DetectLog;
import com.hstba.jingzheng.entity.User;
import com.hstba.jingzheng.mapper.UserMapper;
import com.hstba.jingzheng.service.DetectLogService;
import com.hstba.jingzheng.service.RedisService;
import com.hstba.jingzheng.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private RedisService redisService;
    @Autowired
    private UserMapper userMapper;

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;

    @Value("${tplid}")
    private String tplid;

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
                    senTplMsg(u.getOpenid(), u.getFormId());
                });
            }
        }
        redisService.set("currentState", results + "");
        detectLogService.saveDetectLog(new DetectLog().setResults(results));
    }

    private String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
        if (entity.getStatusCodeValue() == 200) {
            String body = entity.getBody();
            Map<String, Object> res = JSONUtil.toMap(body);
            return String.valueOf(res.get("access_token"));
        }
        return "";
    }

    private void senTplMsg(String openid, String form_id) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + getAccessToken();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("touser", openid);
        map.put("template_id", tplid);
        map.put("page", "index");
        map.put("form_id", "form_id");
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> keyword1 = new HashMap<String, Object>();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        keyword1.put("value", dateFormater.format(date));
        keyword1.put("color", "#173177");
        Map<String, Object> keyword2 = new HashMap<String, Object>();
        keyword2.put("value", "进京证在线申请系统已可用");
        keyword2.put("color", "#173177");
        Map<String, Object> keyword3 = new HashMap<String, Object>();
        keyword3.put("value", "请您尽快登录北京交警App申请进京证");
        keyword3.put("color", "#173177");
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        map.put("data", data);

        String reqJsonStr = JSONUtil.toJson(map);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(reqJsonStr, headers);
        ResponseEntity<Map> resp = this.restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
    }
}