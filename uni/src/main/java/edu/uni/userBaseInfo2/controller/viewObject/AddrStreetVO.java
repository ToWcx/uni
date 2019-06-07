package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.AddrModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/19 21:48
 * @Version 1.0
 */
public class AddrStreetVO {
    private Long AreaCode;

    private List<AddrModel> addrModels;

    public Long getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(Long areaCode) {
        AreaCode = areaCode;
    }

    public List<AddrModel> getAddrModels() {
        return addrModels;
    }

    public void setAddrModels(List<AddrModel> addrModels) {
        this.addrModels = addrModels;
    }
}
