package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class EmployeeHistoryModel {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String descript;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    @Override
    public String toString() {
        return "EmployeeHistoryModel{" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", descript='" + descript + '\'' +
                '}';
    }
}
