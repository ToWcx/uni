package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author wuchuxin
 * @Date 2019/5/14 15:30
 * @Version 1.0
 */
public class UserinfoApplyModel {
    //根据业务id 获取业务名称
    private String approval_main_name;
    //根据申请人id获取申请人姓名
    private String byWho;

    private String applyReason;
    //用户审批流程表的result  0：不通过 1：通过  boolean转String
    private String result;

    private String reason;

    private Long checkWho;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public String getApproval_main_name() {
        return approval_main_name;
    }

    public void setApproval_main_name(String approval_main_name) {
        this.approval_main_name = approval_main_name;
    }

    public String getByWho() {
        return byWho;
    }

    public void setByWho(String byWho) {
        this.byWho = byWho;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getCheckWho() {
        return checkWho;
    }

    public void setCheckWho(Long checkWho) {
        this.checkWho = checkWho;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "UserinfoApplyModel{" +
                "approval_main_name='" + approval_main_name + '\'' +
                ", byWho='" + byWho + '\'' +
                ", applyReason='" + applyReason + '\'' +
                ", result='" + result + '\'' +
                ", reason='" + reason + '\'' +
                ", checkWho=" + checkWho +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
