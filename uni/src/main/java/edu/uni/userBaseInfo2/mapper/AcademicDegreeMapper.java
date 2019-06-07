package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.AcademicDegree;

import java.util.List;

public interface AcademicDegreeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AcademicDegree record);

    int insertSelective(AcademicDegree record);

    AcademicDegree selectByPrimaryKey(Long id);

    //新增
    AcademicDegree selectByName(String name);

    List<AcademicDegree> selectAll();

    int updateByPrimaryKeySelective(AcademicDegree record);

    int updateByPrimaryKey(AcademicDegree record);
}