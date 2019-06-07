package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.AddrCity;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:32
 * @Version 1.0
 */
public interface AddrCityService {

    AddrCity selectByZh(String cityZh);

    List<AddrCity> selectByStateCode(long stateCode);

    AddrCity selectIdByCode(Long code);
}
