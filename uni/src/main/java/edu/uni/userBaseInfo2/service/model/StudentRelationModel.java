package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class StudentRelationModel {
    private Long id;

    private String relaName;

    private Long relaId;

    private String relationship;

    private String relaIdentification;

    private String relaSex;

    private String relaBirthday;

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

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRelaIdentification() {
        return relaIdentification;
    }

    public void setRelaIdentification(String relaIdentification) {
        this.relaIdentification = relaIdentification;
    }

    public String getRelaSex() {
        return relaSex;
    }

    public void setRelaSex(String relaSex) {
        this.relaSex = relaSex;
    }

    public String getRelaBirthday() {
        return relaBirthday;
    }

    public void setRelaBirthday(String relaBirthday) {
        this.relaBirthday = relaBirthday;
    }

    @Override
    public String toString() {
        return "StudentRelationModel{" +
                "id=" + id +
                "relaName='" + relaName + '\'' +
                ", relaId=" + relaId +
                ", relationship='" + relationship + '\'' +
                ", relaIdentification='" + relaIdentification + '\'' +
                ", relaSex='" + relaSex + '\'' +
                ", relaBirthday='" + relaBirthday + '\'' +
                '}';
    }
}
