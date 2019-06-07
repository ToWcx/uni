package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.EmployeeHistory;
import edu.uni.userBaseInfo2.service.model.EmployeeHistoryModel;

import java.util.List;

public interface EmployeeHistoryService {
    //插入新数据
    boolean insert(EmployeeHistory employeeHistory);
    //更新数据
    boolean update(EmployeeHistory employeeHistory);
    //删除数据
    boolean delete(Long id);
    //按照id查找
    //EmployeeHistoryModel select(Long id);
    //查找所有类别
    List<EmployeeHistoryModel> selectAll(long id);

}
