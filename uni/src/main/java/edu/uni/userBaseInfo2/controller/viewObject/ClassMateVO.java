package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.ClassMateModel;

import java.util.List;

public class ClassMateVO {
    private Long userId;

    private List<ClassMateModel> classMateModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ClassMateModel> getClassMateModels() {
        return classMateModels;
    }

    public void setClassMateModels(List<ClassMateModel> classMateModels) {
        this.classMateModels = classMateModels;
    }

    @Override
    public String toString() {
        return "ClassMateVO{" +
                "userId=" + userId +
                ", classMateModels=" + classMateModels +
                '}';
    }
}
