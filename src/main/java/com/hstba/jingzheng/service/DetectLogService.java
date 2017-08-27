package com.hstba.jingzheng.service;

import com.hstba.jingzheng.entity.DetectLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hstba.jingzheng.mapper.DetectLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetectLogService {

    private static Logger logger = LoggerFactory.getLogger(DetectLogService.class);

    @Autowired
    private DetectLogMapper detectLogMapper;

    public void saveDetectLog(DetectLog log) {
        detectLogMapper.insert(log);
    }


    public DetectLog getLastSuccessLog() {
        return detectLogMapper.getLastSuccessLog();
    }
}
