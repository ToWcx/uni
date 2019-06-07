package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.bean.Address;
import edu.uni.userBaseInfo2.service.model.AddressModel;

import java.util.List;

/**
 * 视图模型对象
 * 将核心领域模型用户对象转化为可供UI使用的viewobject
 */
public class AddressVO {
    private Long userId;

    private List<AddressModel> address;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<AddressModel> getAddress() {
        return address;
    }

    public void setAddress(List<AddressModel> address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddressVO{" +
                "userId=" + userId +
                ", address=" + address +
                '}';
    }
}
