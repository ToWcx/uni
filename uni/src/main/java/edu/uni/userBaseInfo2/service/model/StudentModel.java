package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.uni.userBaseInfo2.bean.Address;
import edu.uni.userBaseInfo2.bean.Ecomm;
import edu.uni.userBaseInfo2.bean.StudentRelation;

import java.util.Date;
import java.util.List;

/**
 * 处理业务逻辑的核心领域模型对象
 * 存放Student的所有信息
 */
public class StudentModel {
    private Long id;

    private Long userId;

    private String stuNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginLearnDate;

    private String grade;

    private String major;

    private Long classId;

    private String political;

    private String liveRoom;

    private Long homeAddressId;

    private Long phoneEcommId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Date getBeginLearnDate() {
        return beginLearnDate;
    }

    public void setBeginLearnDate(Date beginLearnDate) {
        this.beginLearnDate = beginLearnDate;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(String liveRoom) {
        this.liveRoom = liveRoom;
    }

    public Long getHomeAddressId() {
        return homeAddressId;
    }

    public void setHomeAddressId(Long homeAddressId) {
        this.homeAddressId = homeAddressId;
    }

    public Long getPhoneEcommId() {
        return phoneEcommId;
    }

    public void setPhoneEcommId(Long phoneEcommId) {
        this.phoneEcommId = phoneEcommId;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", stuNo='" + stuNo + '\'' +
                ", beginLearnDate=" + beginLearnDate +
                ", grade='" + grade + '\'' +
                ", major='" + major + '\'' +
                ", classId=" + classId +
                ", political='" + political + '\'' +
                ", liveRoom='" + liveRoom + '\'' +
                ", homeAddressId=" + homeAddressId +
                ", phoneEcommId=" + phoneEcommId +
                '}';
    }
}
