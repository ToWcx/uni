package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.DisciplineCategory;

public interface DisciplineCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DisciplineCategory record);

    int insertSelective(DisciplineCategory record);

    DisciplineCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DisciplineCategory record);

    int updateByPrimaryKey(DisciplineCategory record);
}