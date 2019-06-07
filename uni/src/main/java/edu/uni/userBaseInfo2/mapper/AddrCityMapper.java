package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.AddrCity;

import java.util.List;

public interface AddrCityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AddrCity record);

    int insertSelective(AddrCity record);

    AddrCity selectByPrimaryKey(Long id);

    AddrCity selectByZh(String addrZh);
    //根据地区code查找地区id
    AddrCity selectIdByCode(Long code);

    List<AddrCity> selectByStateCode(Long code);

    int updateByPrimaryKeySelective(AddrCity record);

    int updateByPrimaryKey(AddrCity record);
}