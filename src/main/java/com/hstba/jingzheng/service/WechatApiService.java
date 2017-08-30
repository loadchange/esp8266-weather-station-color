package com.hstba.jingzheng.service;

import com.hstba.jingzheng.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WechatApiService {
    private static final int ERROR_OK = 0;
    private static final int INVALID_ACCESS_TOKEN = 4001;
    @Autowired
    private RedisService redisService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;

    @Value("${tplid}")
    private String tplid;

    public String getAccessToken(boolean forced) {
        long currentTime = new Date().getTime();
        String cacheAccessToken = redisService.get("access_token");
        String cacheTokenAging = redisService.get("token_aging");
        if (!forced && cacheAccessToken != null && cacheTokenAging != null && Long.valueOf(cacheTokenAging) > currentTime) {
            return cacheAccessToken;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret;
        ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
        if (entity.getStatusCodeValue() == 200) {
            String body = entity.getBody();
            Map<String, Object> res = JSONUtil.toMap(body);
            String access_token = String.valueOf(res.get("access_token"));
            int expires_in = Float.valueOf(String.valueOf(res.get("expires_in"))).intValue();
            long token_aging = currentTime + ((expires_in - 200) * 1000);
            redisService.set("access_token", access_token);
            redisService.set("token_aging", token_aging + "");
            return access_token;
        }
        return "";
    }

    public void senTplMsg(String token, String openid, String form_id) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + token;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("touser", openid);
        map.put("template_id", tplid);
        map.put("page", "pages/index/index");
        map.put("form_id", form_id);
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> keyword1 = new HashMap<String, Object>();
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        keyword1.put("value", dateFormater.format(date));
        keyword1.put("color", "#173177");
        Map<String, Object> keyword2 = new HashMap<String, Object>();
        keyword2.put("value", "请尽快打开北京交警APP办理进京证");
        keyword2.put("color", "#173177");
        Map<String, Object> keyword3 = new HashMap<String, Object>();
        keyword3.put("value", "进京证已开启");
        keyword3.put("color", "#e62117");
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        map.put("data", data);
        map.put("emphasis_keyword", "keyword3.DATA");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<Map<String, Object>>(map, headers);

        ResponseEntity<String> response = this.restTemplate.postForEntity(url, entity, String.class, map);
        System.out.println(response.getBody() + '|' + openid);
        Map<String, Object> res = JSONUtil.toMap(response.getBody());
        int errcode = Float.valueOf(String.valueOf(res.get("errcode"))).intValue();
        if (errcode == INVALID_ACCESS_TOKEN) {
            senTplMsg(getAccessToken(true), openid, form_id);
        }
    }
}
