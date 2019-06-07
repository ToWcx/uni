package edu.uni.userBaseInfo2.service.model;

public class ClassMateModel {

    private String userName;

    private String stuNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    @Override
    public String toString() {
        return "ClassMateModel{" +
                "userName='" + userName + '\'' +
                ", stuNo='" + stuNo + '\'' +
                '}';
    }
}
