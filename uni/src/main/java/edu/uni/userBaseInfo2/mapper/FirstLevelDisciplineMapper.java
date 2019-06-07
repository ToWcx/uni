package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.FirstLevelDiscipline;

public interface FirstLevelDisciplineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FirstLevelDiscipline record);

    int insertSelective(FirstLevelDiscipline record);

    FirstLevelDiscipline selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FirstLevelDiscipline record);

    int updateByPrimaryKey(FirstLevelDiscipline record);
}