package edu.uni.userBaseInfo2.service.model;

import java.util.List;

public class DepartmentToStudentListModel {

    private List<DepartmentToStudentModel> departmentToStudentModels;

    public List<DepartmentToStudentModel> getDepartmentToStudentModels() {
        return departmentToStudentModels;
    }

    public void setDepartmentToStudentModels(List<DepartmentToStudentModel> departmentToStudentModels) {
        this.departmentToStudentModels = departmentToStudentModels;
    }

    @Override
    public String toString() {
        return "DepartmentToStudentListModel{" +
                "departmentToStudentModels=" + departmentToStudentModels +
                '}';
    }
}
