package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.AddrState;

import java.util.List;

public interface AddrStateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddrState record);

    int insertSelective(AddrState record);

    AddrState selectByPrimaryKey(Long id);

    AddrState selectByZh(String addrZh);

    List<AddrState> selectByCountryCode(Long countryCode);
    //根据地区code查找地区id
    AddrState selectIdByCode(Long code);

    int updateByPrimaryKeySelective(AddrState record);

    int updateByPrimaryKey(AddrState record);
}