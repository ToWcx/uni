package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Employee;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long id);

    //查找职员有效信息
    Employee selectByUserIdAndDelete(Long id);

    Employee selectByUserId(Long id);

    int deleteByUserId(Long id);
    
    Employee selectById(Long id);

//    int updateByUserId(Long id);
    //根据表id逻辑删除旧信息
    int updateById(Long id);

    //将记录置为有效
    int updateTrueById(Long id);

    List<Employee> selectAllByDeleted(Employee record);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}