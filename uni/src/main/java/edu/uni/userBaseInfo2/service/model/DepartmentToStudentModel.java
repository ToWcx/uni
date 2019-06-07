package edu.uni.userBaseInfo2.service.model;

import java.util.List;

public class DepartmentToStudentModel {
    private StudentModel studentModels;
    //private String userSex;
    private List<EcommModel> ecommModels;

    private List<AddressModel> addressModels;

    private List<StudentRelationModel>  studentRelationModels;

    private List<LearningDegreeModel> learningDegreeModels;

    public StudentModel getStudentModels() {
        return studentModels;
    }

    public void setStudentModels(StudentModel studentModels) {
        this.studentModels = studentModels;
    }

    public List<EcommModel> getEcommModels() {
        return ecommModels;
    }

    public void setEcommModels(List<EcommModel> ecommModels) {
        this.ecommModels = ecommModels;
    }

    public List<AddressModel> getAddressModels() {
        return addressModels;
    }

    public void setAddressModels(List<AddressModel> addressModels) {
        this.addressModels = addressModels;
    }

    public List<StudentRelationModel> getStudentRelationModels() {
        return studentRelationModels;
    }

    public void setStudentRelationModels(List<StudentRelationModel> studentRelationModels) {
        this.studentRelationModels = studentRelationModels;
    }

    public List<LearningDegreeModel> getLearningDegreeModels() {
        return learningDegreeModels;
    }

    public void setLearningDegreeModels(List<LearningDegreeModel> learningDegreeModels) {
        this.learningDegreeModels = learningDegreeModels;
    }

    @Override
    public String toString() {
        return "DepartmentToStudentModel{" +
                "studentModels=" + studentModels +
                ", ecommModels=" + ecommModels +
                ", addressModels=" + addressModels +
                ", studentRelationModels=" + studentRelationModels +
                ", learningDegreeModels=" + learningDegreeModels +
                '}';
    }
}
