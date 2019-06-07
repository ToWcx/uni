package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.SecondLevelDiscipline;
import org.apache.ibatis.annotations.Param;

public interface SecondLevelDisciplineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecondLevelDiscipline record);

    int insertSelective(SecondLevelDiscipline record);

    SecondLevelDiscipline selectByPrimaryKey(Long id);

    SecondLevelDiscipline selectByZh(@Param("zh") String zh, @Param("id") Long id);

    int updateByPrimaryKeySelective(SecondLevelDiscipline record);

    int updateByPrimaryKey(SecondLevelDiscipline record);
}