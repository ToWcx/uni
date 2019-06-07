package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.Employee;
import edu.uni.userBaseInfo2.service.model.*;

import java.util.List;

public interface EmployeeService {
    //插入新数据
    boolean insert(Employee employee);
    /**
     * 逻辑删除旧记录
     * @param id
     * @return
     */
    boolean updateByUserId(long id);

    /**
     * 根据id将记录置为有效
     * @param id
     * @return
     */
    boolean updateTrueById(long id);
    //删除数据
    boolean delete(Long id);
    //查找类别

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
    //按照class_id查找授课学生信息(包括历史)
    List<ClassToStudentModel> selectdelete1ClassStudent(long id);
    //按照department_id查找同学院学生信息(有效)
    List<DepartmentToStudentListModel> selectdelete0DepartStudent(long id);
    //按照department_id查找同学院学生信息(包括历史)
    List<DepartmentToStudentListModel> selectdelete1DepartStudent(long id);

}
