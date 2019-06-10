package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.EmployeeHistory;

import java.util.List;

public interface EmployeeHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EmployeeHistory record);

    int insertSelective(EmployeeHistory record);

    EmployeeHistory selectByPrimaryKey(Long id);

    //新增查询： 根据user_id查找该用户所有地址信息
    List<EmployeeHistory> selectByUserId(Long id);
    //逻辑删除旧记录
    int updateById(Long id);
    //将记录置为有效
    int updateTrueById(Long id);

    int updateByPrimaryKeySelective(EmployeeHistory record);

    int updateByPrimaryKey(EmployeeHistory record);
}