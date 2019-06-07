package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * 处理审批中心传给前端的数据
 * @Author wuchuxin
 * @Date 2019/5/16 8:37
 * @Version 1.0
 */
public class ApprovalModel {
    //审批流程表id
    private long userinfoApplyApprovalId;
    //新纪录id
    private Long newInfoId;
    //旧记录id
    private Long oldInfoId;
    //业务名称
    private String approvalName;
    //申请人id
    private long applyUserId;
    //申请理由
    private String reanson;
    //申请时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    //上个审批人姓名
    private long byWho;
    //第几步审批
    private int step;
    //审核结果
//    private boolean result;
    private int result;
    //每一步的审核结果
    private List<ApprovalStepModel> approvalStepModels;
    // 审核人姓名
    private long checkWho;
    // 审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    private int deleted;

    public long getUserinfoApplyApprovalId() {
        return userinfoApplyApprovalId;
    }

    public void setUserinfoApplyApprovalId(long userinfoApplyApprovalId) {
        this.userinfoApplyApprovalId = userinfoApplyApprovalId;
    }

    public Long getNewInfoId() {
        return newInfoId;
    }

    public void setNewInfoId(Long newInfoId) {
        this.newInfoId = newInfoId;
    }

    public Long getOldInfoId() {
        return oldInfoId;
    }

    public void setOldInfoId(Long oldInfoId) {
        this.oldInfoId = oldInfoId;
    }

    public String getApprovalName() {
        return approvalName;
    }

    public void setApprovalName(String approvalName) {
        this.approvalName = approvalName;
    }

    public long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getReanson() {
        return reanson;
    }

    public void setReanson(String reanson) {
        this.reanson = reanson;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getByWho() {
        return byWho;
    }

    public void setByWho(long byWho) {
        this.byWho = byWho;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<ApprovalStepModel> getApprovalStepModels() {
        return approvalStepModels;
    }

    public void setApprovalStepModels(List<ApprovalStepModel> approvalStepModels) {
        this.approvalStepModels = approvalStepModels;
    }

    public long getCheckWho() {
        return checkWho;
    }

    public void setCheckWho(long checkWho) {
        this.checkWho = checkWho;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "ApprovalModel{" +
                "userinfoApplyApprovalId=" + userinfoApplyApprovalId +
                ", newInfoId=" + newInfoId +
                ", oldInfoId=" + oldInfoId +
                ", approvalName='" + approvalName + '\'' +
                ", applyUserId=" + applyUserId +
                ", reanson='" + reanson + '\'' +
                ", startTime=" + startTime +
                ", byWho=" + byWho +
                ", step=" + step +
                ", result=" + result +
                ", approvalStepModels=" + approvalStepModels +
                ", checkWho=" + checkWho +
                ", checkTime=" + checkTime +
                ", deleted=" + deleted +
                '}';
    }
}

