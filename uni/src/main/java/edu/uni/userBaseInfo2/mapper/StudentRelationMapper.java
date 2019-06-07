package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.StudentRelation;

import java.util.List;

public interface StudentRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StudentRelation record);

    int insertSelective(StudentRelation record);

    StudentRelation selectByPrimaryKey(Long id);

    //
    List<StudentRelation> selectByUserId(Long id);

    int updateByPrimaryKeySelective(StudentRelation record);

    int updateByPrimaryKey(StudentRelation record);
}