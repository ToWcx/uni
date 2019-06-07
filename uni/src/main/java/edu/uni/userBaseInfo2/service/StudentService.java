package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.service.model.ClassMateModel;
import edu.uni.userBaseInfo2.service.model.StudentModel;

import java.util.List;

public interface StudentService {
    /**
     * 保存新类别
     * @param student
     * @return
     */
    boolean insert(Student student);

    /**
     * 删除类别
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 更新类别
     * @param student
     * @return
     */
    boolean update(Student student);

    /**
     * 根据userId 逻辑删除旧记录
     * @param userId
     * @return
     */
    boolean updateByUserId(long userId);

    /**
     * 根据id 查找
     * @param id
     * @return
     */
    boolean updateTrueById(long id);

    /**
     * 查找类别
     * @param id
     * @return
     */
    StudentModel select(long id);

    /**
     * 根据id查找学生记录
     * @param id
     * @return
     */
    Student selectBean(long id);

    /**
     * 查找学生所有信息
     * @param id
     * @return
     */
//    List<StudentModel> selectAllById(long id);

    /**
     * 查找所有类别
     * @return
     */
    List<Student> selectAll();

//    /**
//     * 根据user_id查找用户所有信息
//     * @return
//     */
//     StudentInfoModel selectInfoByUserId(long id);

    /**
     * 查找学生历史信息
     * @param id
     * @return
     */
    List<StudentModel> selectStudentHistory(long id);

    /**
     * 查找学生同班同学部分信息
     * @param id
     * @return
     */
    List<ClassMateModel> selectClassMate(long id);

    /**
     * 根据班级id查找班级同学部分信息
     * @param id
     * @return
     */
    List<ClassMateModel> selectByClassId(long id);
}
