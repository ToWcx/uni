package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.AddrStreet;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:32
 * @Version 1.0
 */
public interface AddrStreetService {

    AddrStreet selectByZh(String streetZh);

    List<AddrStreet> selectByAreaCode(long areaCode);

    AddrStreet selectIdByCode(Long code);
}
