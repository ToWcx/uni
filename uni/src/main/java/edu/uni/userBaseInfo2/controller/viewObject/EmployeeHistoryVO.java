package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.EmployeeHistoryModel;

import java.util.List;

public class EmployeeHistoryVO {

    private Long userId;
    private List<EmployeeHistoryModel> employeeHistoryModel;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<EmployeeHistoryModel> getEmployeeHistoryModel() {
        return employeeHistoryModel;
    }

    public void setEmployeeHistoryModel(List<EmployeeHistoryModel> employeeHistoryModel) {
        this.employeeHistoryModel = employeeHistoryModel;
    }

    @Override
    public String toString() {
        return "EmployeeHistoryVO{" +
                "userId=" + userId +
                ", employeeHistoryModel=" + employeeHistoryModel +
                '}';
    }
}
