package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.AddrModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/19 21:48
 * @Version 1.0
 */
public class AddrCityVO {
    private Long StateCode;

    private List<AddrModel> addrModels;

    public Long getStateCode() {
        return StateCode;
    }

    public void setStateCode(Long stateCode) {
        StateCode = stateCode;
    }

    public List<AddrModel> getAddrModels() {
        return addrModels;
    }

    public void setAddrModels(List<AddrModel> addrModels) {
        this.addrModels = addrModels;
    }
}
