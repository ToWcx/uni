package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.PictureModel;

import java.util.List;

public class PictureVO {

    private Long UserId;

    private List<PictureModel> pictureModels;

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public List<PictureModel> getPictureModels() {
        return pictureModels;
    }

    public void setPictureModels(List<PictureModel> pictureModels) {
        this.pictureModels = pictureModels;
    }

    @Override
    public String toString() {
        return "PictureVO{" +
                "UserId=" + UserId +
                ", pictureModels=" + pictureModels +
                '}';
    }
}
