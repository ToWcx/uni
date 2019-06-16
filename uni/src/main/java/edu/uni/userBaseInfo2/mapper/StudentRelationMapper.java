package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.StudentRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface StudentRelationMapper {


    int deleteByPrimaryKey(Long id);

    int insert(StudentRelation record);

    int insertSelective(StudentRelation record);

    // id
    StudentRelation selectByPrimaryKey(Long id);

    List<StudentRelation> selectByRelation(@Param("userId") Long id,@Param("relationship") Integer Relation);
    //user_id
    //新增查询： 根据user_id查找该用户所有地址信息
    List<StudentRelation> selectByUserId(Long id);


    int updateByPrimaryKeySelective(StudentRelation record);

    int updateByPrimaryKey(StudentRelation record);
}