package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.AddrStreet;

import java.util.List;

public interface AddrStreetMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddrStreet record);

    int insertSelective(AddrStreet record);

    AddrStreet selectByPrimaryKey(Long id);

    AddrStreet selectByZh(String addrZh);

    List<AddrStreet> selectByAreaCode(Long code);
    //根据地区code查找地区id
    AddrStreet selectIdByCode(Long code);

    int updateByPrimaryKeySelective(AddrStreet record);

    int updateByPrimaryKey(AddrStreet record);
}