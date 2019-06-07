package edu.uni.userBaseInfo2.service.model;

public class PictureModel {
    private String universityId;

    private String pictureName;

    private Integer flag;

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PictureModel{" +
                "universityId=" + universityId +
                ", pictureName='" + pictureName + '\'' +
                ", flag=" + flag +
                '}';
    }
}
