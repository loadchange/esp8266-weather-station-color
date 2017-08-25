package com.hstba.jingzheng.controller;

import com.hstba.jingzheng.entity.DetectLog;
import com.hstba.jingzheng.mapper.DetectLogMapper;
import com.hstba.jingzheng.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class IndexController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/current-state.json", method = RequestMethod.GET)
    public DetectLog currentState() {
        DetectLog log = new DetectLog();
        log.setTime(new Date(Long.valueOf(redisService.get("lastOpenTime"))));
        log.setResults(Integer.valueOf(redisService.get("currentState")));
        return log;
    }
}
