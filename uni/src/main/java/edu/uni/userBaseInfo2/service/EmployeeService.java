package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.Employee;
import edu.uni.userBaseInfo2.service.model.*;

import java.util.List;

public interface EmployeeService {

    boolean insert(Employee employee);

//    /**
//     * 逻辑删除旧记录
//     * @param id
//     * @return
//     */
//    boolean updateByUserId(long id);

    /**
     * 根据表id 逻辑删除旧记录
     * @param id
     * @return
     */
    boolean updateById(long id);

    /**
     * 根据id将记录置为有效
     * @param id
     * @return
     */
    boolean updateTrueById(long id);

    boolean delete(Long id);

    /**
     * 查询当前有效教职工信息
     * @param id
     * @return
     */
    EmployeeModel select(long id);
    /**
     * 根据表id查询职员信息
     * @param id
     * @return
     */
    Employee selectById(long id);

    //查询教职工信息(包括历史)
    EmployeeModel selectwithoutdelete(long id);

    //查找所有类别
    List<Employee> selectAll();

    //按照class_id查找授课学生信息(有效)
    List<ClassToStudentListModel> selectdelete0ClassStudent(long id);
    //按照department_id查找同学院学生信息(包括历史)
    List<DepartmentToStudentListModel> selectdelete1DepartStudent(long id);
    //按照department_id查找老师所授课的课程信息
    List<TeachClassModel> selectTeachClass(long id);
    //按照employeeid查看所带班级的全部学生信息(班主任)
    List<TeachClassModel> selectClassStuForHeadteacher(long id);
    //按照userid查看所处学院的所有班级信息
    List<TeachClassModel> selectDepartment(long id);
    //显示所有老师信息
    List<EmployeeListModel> showEmployeeList(long id);
}
