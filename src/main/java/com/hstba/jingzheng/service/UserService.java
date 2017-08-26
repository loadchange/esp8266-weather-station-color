package com.hstba.jingzheng.service;

import com.hstba.jingzheng.entity.User;
import com.hstba.jingzheng.mapper.UserMapper;
import com.hstba.jingzheng.util.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;

    public User getUserByOpenId(String openId) {
        return userMapper.getUserByOpenId(openId);
    }

    public void updateUser(User user) {
        userMapper.update(user);
    }

    public void setRemind(int remind, String openid) {
        userMapper.setRemind(remind, openid);
    }

    public String login(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
        if (entity.getStatusCodeValue() == 200) {
            String body = entity.getBody();
            Map<String, Object> res = JSONUtil.toMap(body);
            return String.valueOf(res.get("openid"));
        }
        return "";
    }
}
