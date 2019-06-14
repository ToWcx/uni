package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.TeachClassModel;

import java.util.List;

public class TeachClassVO {
    private String department;

    private List<TeachClassModel> teachClassModels;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<TeachClassModel> getTeachClassModels() {
        return teachClassModels;
    }

    public void setTeachClassModels(List<TeachClassModel> teachClassModels) {
        this.teachClassModels = teachClassModels;
    }

    @Override
    public String toString() {
        return "TeachClassVO{" +
                "department='" + department + '\'' +
                ", teachClassModels=" + teachClassModels +
                '}';
    }
}
