package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;

import java.util.Date;
import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/6/8 16:00
 * @Version 1.0
 */
public class UserinfoApplyResultModel {
    //用户查询自己的申请结果
    private String applyReason;

    private int applyResult;

    private Long oldInfoId;

    private Long newInfoId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

//    private Integer infoType;

    private List<ApprovalStepModel> approvalStepModels;

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public int getApplyResult() {
        return applyResult;
    }

    public void setApplyResult(int applyResult) {
        this.applyResult = applyResult;
    }

    public Long getOldInfoId() {
        return oldInfoId;
    }

    public void setOldInfoId(Long oldInfoId) {
        this.oldInfoId = oldInfoId;
    }

    public Long getNewInfoId() {
        return newInfoId;
    }

    public void setNewInfoId(Long newInfoId) {
        this.newInfoId = newInfoId;
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

    public List<ApprovalStepModel> getApprovalStepModels() {
        return approvalStepModels;
    }

    public void setApprovalStepModels(List<ApprovalStepModel> approvalStepModels) {
        this.approvalStepModels = approvalStepModels;
    }

    @Override
    public String toString() {
        return "UserinfoApplyResultModel{" +
                "applyReason='" + applyReason + '\'' +
                ", applyResult=" + applyResult +
                ", oldInfoId=" + oldInfoId +
                ", newInfoId=" + newInfoId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", approvalStepModels=" + approvalStepModels +
                '}';
    }
}
