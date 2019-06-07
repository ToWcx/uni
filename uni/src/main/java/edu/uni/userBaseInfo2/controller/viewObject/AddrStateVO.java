package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.AddrModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/19 21:47
 * @Version 1.0
 */
public class AddrStateVO {
    private Long CountryCode;

    private List<AddrModel> addrModels;

    public Long getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(Long countryCode) {
        CountryCode = countryCode;
    }

    public List<AddrModel> getAddrModels() {
        return addrModels;
    }

    public void setAddrModels(List<AddrModel> addrModels) {
        this.addrModels = addrModels;
    }
}
