package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.LearningDegree;

import java.util.List;

public interface LearningDegreeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LearningDegree record);

    int insertSelective(LearningDegree record);

    LearningDegree selectByPrimaryKey(Long id);

    List<LearningDegree> selectByUserId(Long id);

    //逻辑删除旧记录
    int updateById(Long id);

    int updateTrueById(Long id);

    int updateByPrimaryKeySelective(LearningDegree record);

    int updateByPrimaryKey(LearningDegree record);
}