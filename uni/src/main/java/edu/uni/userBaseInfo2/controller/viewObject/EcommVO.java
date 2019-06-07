package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.bean.Ecomm;
import edu.uni.userBaseInfo2.service.model.EcommModel;

import java.util.List;

public class EcommVO {
    private Long userId;

    private List<EcommModel> ecomms;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<EcommModel> getEcomms() {
        return ecomms;
    }

    public void setEcomms(List<EcommModel> ecomms) {
        this.ecomms = ecomms;
    }

    @Override
    public String toString() {
        return "EcommVO{" +
                "userId=" + userId +
                ", ecomms=" + ecomms +
                '}';
    }
}
