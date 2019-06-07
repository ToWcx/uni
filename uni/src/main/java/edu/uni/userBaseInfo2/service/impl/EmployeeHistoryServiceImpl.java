package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.EmployeeHistory;
import edu.uni.userBaseInfo2.mapper.EmployeeHistoryMapper;
import edu.uni.userBaseInfo2.service.EmployeeHistoryService;
import edu.uni.userBaseInfo2.service.model.EmployeeHistoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeHistoryServiceImpl implements EmployeeHistoryService {
    @Autowired
    private EmployeeHistoryMapper employeeHistoryMapper;

    @Override
    public boolean insert(EmployeeHistory employeeHistory) {
        return employeeHistoryMapper.insert(employeeHistory)>0 ? true:false;
    }

    @Override
    public boolean update(EmployeeHistory employeeHistory) {
        return employeeHistoryMapper.updateByPrimaryKeySelective(employeeHistory)>0 ? true : false;
    }

    @Override
    public boolean delete(Long id) {
        return employeeHistoryMapper.deleteByPrimaryKey(id)>0 ?  true : false;
    }

    @Override
    public List<EmployeeHistoryModel> selectAll(long id) {
        List<EmployeeHistory> employeeHistories = employeeHistoryMapper.selectByUserId(id);
        List<EmployeeHistoryModel> employeeHistoryModels = convertFromBean(employeeHistories);
        System.out.println(employeeHistoryModels);

        return employeeHistoryModels;
    }
    private List<EmployeeHistoryModel> convertFromBean(List<EmployeeHistory> employeeHistories){
        List<EmployeeHistoryModel> employeeHistoryModels = new ArrayList<>();
        for(int i=0;i<employeeHistories.size();i++){
            EmployeeHistoryModel employeeHistoryModel = new EmployeeHistoryModel();
            employeeHistoryModel.setBeginTime(employeeHistories.get(i).getBeginTime());
            employeeHistoryModel.setEndTime(employeeHistories.get(i).getEndTime());
            employeeHistoryModel.setDescript(employeeHistories.get(i).getDescript());
            employeeHistoryModels.add(employeeHistoryModel);
        }
        return employeeHistoryModels;
    }

}
