package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ClassToStudentModel {

        private Long UserId;
        private String stuNo;
        private String UserName;
        private String major;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date begin_learn_date;
        private Long majorId;
        private String grade;
        private String sex;
        private String content;
        private Long piliticalId;
        private String political;

    public Date getBegin_learn_date() {
        return begin_learn_date;
    }

    public void setBegin_learn_date(Date begin_learn_date) {
        this.begin_learn_date = begin_learn_date;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Long getPiliticalId() {
        return piliticalId;
    }

    public void setPiliticalId(Long piliticalId) {
        this.piliticalId = piliticalId;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    @Override
    public String toString() {
        return "ClassToStudentModel{" +
                "UserId=" + UserId +
                ", stuNo='" + stuNo + '\'' +
                ", UserName='" + UserName + '\'' +
                ", major='" + major + '\'' +
                ", begin_learn_date=" + begin_learn_date +
                ", majorId='" + majorId + '\'' +
                ", grade='" + grade + '\'' +
                ", sex='" + sex + '\'' +
                ", content='" + content + '\'' +
                ", piliticalId='" + piliticalId + '\'' +
                ", political='" + political + '\'' +
                '}';
    }
}
