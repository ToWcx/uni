package edu.uni.userBaseInfo2.service.model;

/**
 * @Author wuchuxin
 * @Date 2019/5/11 17:13
 * @Version 1.0
 */
public class ApprovalMainModel {
    private Long universityId;

    private String name;

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ApprovalMainModel{" +
                "universityId=" + universityId +
                ", name='" + name + '\'' +
                '}';
    }
}
