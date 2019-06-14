package edu.uni.userBaseInfo2.service.model;

public class TeachClassModel {

    private Long ClassId;
    private String ClassCode;
    private String ClassName;
    private int menbernunmber;
    private String headteacher;
    private String position;

    public String getClassCode() {
        return ClassCode;
    }

    public void setClassCode(String classCode) {
        ClassCode = classCode;
    }

    public Long getClassId() {
        return ClassId;
    }

    public void setClassId(Long classId) {
        ClassId = classId;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getMenbernunmber() {
        return menbernunmber;
    }

    public void setMenbernunmber(int menbernunmber) {
        this.menbernunmber = menbernunmber;
    }

    public String getHeadteacher() {
        return headteacher;
    }

    public void setHeadteacher(String headteacher) {
        this.headteacher = headteacher;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "TeachClassModel{" +
                "ClassId=" + ClassId +
                ", ClassCode='" + ClassCode + '\'' +
                ", ClassName='" + ClassName + '\'' +
                ", menbernunmber=" + menbernunmber +
                ", headteacher='" + headteacher + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
