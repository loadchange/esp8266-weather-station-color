package com.hstba.jingzheng.entity;

import java.util.Date;

public class DetectLog {

    private int id;
    private Date time;
    private int results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getResults() {
        return results;
    }

    public DetectLog setResults(int results) {
        this.results = results;
        return this;
    }

}
