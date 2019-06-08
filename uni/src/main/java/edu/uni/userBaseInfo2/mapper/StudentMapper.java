package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Student;

import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long id);

    //新增
    //查找学生所有历史信息
    List<Student> selectByUserId(Long id);
    //根据user_id查找学生有效信息
    Student selectByUserIdAndDelete(Long id);
    //查找所有学生有效信息
    List<Student> selectAllByDeleted();
    //根据classId查找班级所有学生学号信息
    List<Student> selectStuNosByClass(Long classId);
    //根据classId查找班级所有学生userId信息
    List<Student> selectIdsByClass(Long classId);
    //把记录置为有效
    int updateTrueById(Long id);
    //逻辑删除旧记录
    int updateById(Long id);

//    int updateByUserId(Long userId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}