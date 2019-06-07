package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.AddrState;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:32
 * @Version 1.0
 */
public interface AddrStateService {

    /**
     * 根据省份中文名找到省份id
     * @param stateZh
     * @return
     */
    AddrState selectByZh(String stateZh);

    List<AddrState> selectByCountryCode(long countryCode);

    AddrState selectIdByCode(Long code);
}
