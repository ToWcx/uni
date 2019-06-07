package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.PoliticalAffiliation;

public interface PoliticalAffiliationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PoliticalAffiliation record);

    int insertSelective(PoliticalAffiliation record);

    PoliticalAffiliation selectByPrimaryKey(Long id);

    PoliticalAffiliation selectByZh(String zh);

    int updateByPrimaryKeySelective(PoliticalAffiliation record);

    int updateByPrimaryKey(PoliticalAffiliation record);
}