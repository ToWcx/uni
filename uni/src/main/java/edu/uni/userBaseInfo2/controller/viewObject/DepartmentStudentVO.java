package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.DepartmentToStudentListModel;

import java.util.List;

public class DepartmentStudentVO {
    private Long userId;
    private List<DepartmentToStudentListModel> departmentToStudentListModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<DepartmentToStudentListModel> getDepartmentToStudentListModels() {
        return departmentToStudentListModels;
    }

    public void setDepartmentToStudentListModels(List<DepartmentToStudentListModel> departmentToStudentListModels) {
        this.departmentToStudentListModels = departmentToStudentListModels;
    }

    @Override
    public String toString() {
        return "DepartmentStudentVO{" +
                "userId=" + userId +
                ", departmentToStudentListModels=" + departmentToStudentListModels +
                '}';
    }
}
