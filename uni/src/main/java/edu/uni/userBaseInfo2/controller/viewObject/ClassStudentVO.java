package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.ClassToStudentListModel;
import edu.uni.userBaseInfo2.service.model.ClassToStudentModel;

import java.util.List;

public class ClassStudentVO {
    private Long userId;
    private List<ClassToStudentListModel> classToStudentListModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ClassToStudentListModel> getClassToStudentListModels() {
        return classToStudentListModels;
    }

    public void setClassToStudentListModels(List<ClassToStudentListModel> classToStudentListModels) {
        this.classToStudentListModels = classToStudentListModels;
    }

    @Override
    public String toString() {
        return "ClassStudentVO{" +
                "userId=" + userId +
                ", classToStudentListModels=" + classToStudentListModels +
                '}';
    }
}
