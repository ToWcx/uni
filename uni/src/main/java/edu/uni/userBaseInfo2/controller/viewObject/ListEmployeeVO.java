package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.EmployeeListModel;

import java.util.List;

public class ListEmployeeVO {
    private Long userId;
    private List<EmployeeListModel> employeeListModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<EmployeeListModel> getEmployeeListModels() {
        return employeeListModels;
    }

    public void setEmployeeListModels(List<EmployeeListModel> employeeListModels) {
        this.employeeListModels = employeeListModels;
    }

    @Override
    public String toString() {
        return "ListEmployeeVO{" +
                "userId=" + userId +
                ", employeeListModels=" + employeeListModels +
                '}';
    }
}
