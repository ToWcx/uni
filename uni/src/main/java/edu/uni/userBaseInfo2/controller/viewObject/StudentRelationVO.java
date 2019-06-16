package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.bean.StudentRelation;
import edu.uni.userBaseInfo2.service.model.StudentRelationModel;

import java.util.List;

public class StudentRelationVO {
    private Long userId;

    private List<StudentRelationModel> studentRelationModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<StudentRelationModel> getStudentRelationModels() {
        return studentRelationModels;
    }

    public void setStudentRelationModels(List<StudentRelationModel> studentRelationModels) {
        this.studentRelationModels = studentRelationModels;
    }
}
