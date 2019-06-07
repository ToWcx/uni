package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.StudentModel;

import java.util.List;

public class StudentVO {
    private  Long userId;

    private List<StudentModel> studentModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<StudentModel> getStudentModels() {
        return studentModels;
    }

    public void setStudentModels(List<StudentModel> studentModels) {
        this.studentModels = studentModels;
    }

    @Override
    public String toString() {
        return "StudentVO{" +
                "userId=" + userId +
                ", studentModels=" + studentModels +
                '}';
    }
}
