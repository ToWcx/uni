package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class StudentRelationModel {
    private Long id;

    private String relaName;

    private Long relaId;

    private String relationship;

    private String identification;;

    private String userSex;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userBirthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelaName() {
        return relaName;
    }

    public void setRelaName(String relaName) {
        this.relaName = relaName;
    }
    public Long getRelaId() {
        return relaId;
    }

    public void setRelaId(Long relaId) {
        this.relaId = relaId;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    @Override
    public String toString() {
        return "StudentRelationModel{" +
                "relaName='" + relaName + '\'' +
                ", relaId=" + relaId +
                ", relationship='" + relationship + '\'' +
                ", rela_Identification='" + identification + '\'' +
                ", rela_Sex='" + userSex + '\'' +
                ", rel_aBirthday='" + userBirthday + '\'' +
                '}';
    }
}
