package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.AddrCountry;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:27
 * @Version 1.0
 */
public interface AddrCountryService {

    AddrCountry selectByZh(String countryZh);

    List<AddrCountry> selectAll();

    AddrCountry selectIdByCode(Long code);
}
