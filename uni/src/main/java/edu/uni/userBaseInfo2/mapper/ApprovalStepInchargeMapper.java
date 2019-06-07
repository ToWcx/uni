package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.ApprovalStepIncharge;
import org.apache.ibatis.annotations.Param;

public interface ApprovalStepInchargeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApprovalStepIncharge record);

    int insertSelective(ApprovalStepIncharge record);

    ApprovalStepIncharge selectByPrimaryKey(Long id);
    //根据审批步数表id查找审批步骤详情
    ApprovalStepIncharge selectByAMIdAndStep(@Param("id") Long id, @Param("step") int step);

    int updateByPrimaryKeySelective(ApprovalStepIncharge record);

    int updateByPrimaryKey(ApprovalStepIncharge record);
}