package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.AddrArea;

import java.util.List;

public interface AddrAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddrArea record);

    int insertSelective(AddrArea record);

    AddrArea selectByPrimaryKey(Long id);

    AddrArea selectByZh(String addrZh);
    //根据地区code查找地区id
    AddrArea selectIdByCode(Long code);

    List<AddrArea> selectByCityCode(Long code);

    int updateByPrimaryKeySelective(AddrArea record);

    int updateByPrimaryKey(AddrArea record);
}