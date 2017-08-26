package com.hstba.jingzheng.controller;

import com.hstba.jingzheng.entity.DetectLog;
import com.hstba.jingzheng.entity.User;
import com.hstba.jingzheng.service.RedisService;
import com.hstba.jingzheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/current-state.json", method = RequestMethod.GET)
    public Map<String, Object> currentState() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("lastOpenTime", Long.valueOf(redisService.get("lastOpenTime")));
        result.put("currentState", Integer.valueOf(redisService.get("currentState")));
        return result;
    }

    @RequestMapping(value = "/get-user-switch-state.json", method = RequestMethod.GET)
    public Map<String, Object> getUserSwitchState(@RequestParam("openid") String openid) {
        Map<String, Object> result = new HashMap<String, Object>();
        User user = userService.getUserByOpenId(openid);
        if (user != null) {
            result.put("success", true);
            result.put("remind", user.getRemind());
        }
        return result;
    }

    @RequestMapping(value = "/sync-user-info.json", method = RequestMethod.POST)
    public Map<String, Object> syncUserInfo(User user) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        userService.updateUser(user);
        return result;
    }

    @RequestMapping(value = "/login.json", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam("code") String code) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("openid", userService.login(code));
        return result;
    }

    @RequestMapping(value = "/switch-remind.json", method = RequestMethod.POST)
    public Map<String, Object> switchRemind(
            @RequestParam("state") int remind,
            @RequestParam("openid") String openid,
            @RequestParam("formId") String formId
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        userService.setRemind(remind, openid,formId);
        return result;
    }
}
