package edu.uni.userBaseInfo2.service.model;

import java.util.List;

public class ClassToStudentListModel {

    private List<ClassToStudentModel> classToStudentModels;

    public List<ClassToStudentModel> getClassToStudentModels() {
        return classToStudentModels;
    }

    public void setClassToStudentModels(List<ClassToStudentModel> classToStudentModels) {
        this.classToStudentModels = classToStudentModels;
    }

    @Override
    public String toString() {
        return "ClassToStudentListModel{" +
                "classToStudentModels=" + classToStudentModels +
                '}';
    }
}
