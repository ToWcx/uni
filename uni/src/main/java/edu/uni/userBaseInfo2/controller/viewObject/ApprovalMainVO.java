package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.bean.ApprovalMain;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/11 20:55
 * @Version 1.0
 */
public class ApprovalMainVO {

    private Long universityId;

    private List<ApprovalMain> approvalMainList;

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public List<ApprovalMain> getApprovalMainList() {
        return approvalMainList;
    }

    public void setApprovalMainList(List<ApprovalMain> approvalMainList) {
        this.approvalMainList = approvalMainList;
    }

    @Override
    public String toString() {
        return "ApprovalMainVO{" +
                "universityId=" + universityId +
                ", approvalMainList=" + approvalMainList +
                '}';
    }
}
