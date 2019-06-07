package edu.uni.userBaseInfo2.service.model;

import java.util.List;

public class ClassToStudentModel {

        private String stuNo;
        private String UserName;
        private List<EcommModel> ecommModels;

        private PictureModel pictureModel;

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

    public List<EcommModel> getEcommModels() {
        return ecommModels;
    }

    public void setEcommModels(List<EcommModel> ecommModels) {
        this.ecommModels = ecommModels;
    }

    public PictureModel getPictureModel() {
        return pictureModel;
    }

    public void setPictureModel(PictureModel pictureModel) {
        this.pictureModel = pictureModel;
    }

    @Override
    public String toString() {
        return "ClassToStudentModel{" +
                "stuNo='" + stuNo + '\'' +
                ", UserName='" + UserName + '\'' +
                ", ecommModels=" + ecommModels +
                ", pictureModel=" + pictureModel +
                '}';
    }
}
