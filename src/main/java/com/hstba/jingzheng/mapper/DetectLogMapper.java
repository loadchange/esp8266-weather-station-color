package com.hstba.jingzheng.mapper;

import com.hstba.jingzheng.entity.DetectLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface DetectLogMapper {

    @Select("SELECT * FROM detect_log where results = 1 order by time desc limit 1")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "time", column = "time"),
            @Result(property = "results", column = "results")
    })
    DetectLog getLastSuccessLog();

    @Insert({"INSERT INTO detect_log(time,results) VALUES(NOW(), #{results})"})
    void insert(DetectLog log);
}
