package edu.uni.userBaseInfo2.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class LearningDegreeModel {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String country;

    private String city;

    private String school;

    private String academic;

    private String degree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAcademic() {
        return academic;
    }

    public void setAcademic(String academic) {
        this.academic = academic;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "LearningDegreeModel{" +
                "id=" + id +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", school='" + school + '\'' +
                ", academic='" + academic + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }
}
