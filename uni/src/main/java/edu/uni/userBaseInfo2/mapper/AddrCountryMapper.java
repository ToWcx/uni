package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.AddrCountry;

import java.util.List;

public interface AddrCountryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddrCountry record);

    int insertSelective(AddrCountry record);

    AddrCountry selectByPrimaryKey(Long id);

    AddrCountry selectByZh(String addrZh);

    List<AddrCountry> selectAll();
    //根据地区code查找地区id
    AddrCountry selectIdByCode(Long code);

    int updateByPrimaryKeySelective(AddrCountry record);

    int updateByPrimaryKey(AddrCountry record);
}