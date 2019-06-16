package edu.uni.userBaseInfo2.controller.approvalUtil;

/**
 * @Author wuchuxin
 * @Date 2019/6/16 11:01
 * @Version 1.0
 */
public class StudentRelationAU {
    private Long userId;
    private String relation;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "StudentRelationAU{" +
                "userId=" + userId +
                ", relation='" + relation + '\'' +
                '}';
    }
}
