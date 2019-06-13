package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);
    //新增
    //根据id查找同班所有同学姓名
    List<User> selectUserNamesByIds(@Param("ids")List<Student> ids);
    //根据userId查到该user的学校id
    User selectUniIdById(Long id);
    //查找所有用户(包括作废的)
    List<User> selectAll();

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserNameByUserId(Long id);
}