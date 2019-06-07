package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.LearningDegreeModel;

import java.util.List;

public class LearningDegreeVO {

    private Long userId;

    private List<LearningDegreeModel> learningDegreeModels;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<LearningDegreeModel> getLearningDegreeModels() {
        return learningDegreeModels;
    }

    public void setLearningDegreeModels(List<LearningDegreeModel> learningDegreeModels) {
        this.learningDegreeModels = learningDegreeModels;
    }

    @Override
    public String toString() {
        return "LearningDegreeVO{" +
                "userId=" + userId +
                ", learningDegreeModels=" + learningDegreeModels +
                '}';
    }
}
