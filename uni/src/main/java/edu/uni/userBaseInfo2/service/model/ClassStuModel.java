package edu.uni.userBaseInfo2.service.model;

import java.util.List;

public class ClassStuModel {
    private StudentModel studentModel;

    private UserModel userModel;

    private List<AddressModel> addressModels;

    private List<EcommModel> ecommModels;

    private List<LearningDegreeModel> learningDegreeModels;

    public StudentModel getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public List<AddressModel> getAddressModels() {
        return addressModels;
    }

    public void setAddressModels(List<AddressModel> addressModels) {
        this.addressModels = addressModels;
    }

    public List<EcommModel> getEcommModels() {
        return ecommModels;
    }

    public void setEcommModels(List<EcommModel> ecommModels) {
        this.ecommModels = ecommModels;
    }

    public List<LearningDegreeModel> getLearningDegreeModels() {
        return learningDegreeModels;
    }

    public void setLearningDegreeModels(List<LearningDegreeModel> learningDegreeModels) {
        this.learningDegreeModels = learningDegreeModels;
    }

    @Override
    public String toString() {
        return "ClassStuModel{" +
                "studentModel=" + studentModel +
                ", userModel=" + userModel +
                ", addressModels=" + addressModels +
                ", ecommModels=" + ecommModels +
                ", learningDegreeModels=" + learningDegreeModels +
                '}';
    }
}
