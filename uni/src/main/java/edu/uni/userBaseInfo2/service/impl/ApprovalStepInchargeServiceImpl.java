package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.ApprovalStepIncharge;
import edu.uni.userBaseInfo2.mapper.ApprovalStepInchargeMapper;
import edu.uni.userBaseInfo2.service.ApprovalStepInchargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wuchuxin
 * @Date 2019/5/15 20:17
 * @Version 1.0
 */
@Service
public class ApprovalStepInchargeServiceImpl implements ApprovalStepInchargeService {

    @Autowired
    private ApprovalStepInchargeMapper approvalStepInchargeMapper;

    /**
     * 根据审批业务id和审批步数查找审批步骤详情记录
     * @param amId
     * @param step
     * @return
     */
    @Override
    public ApprovalStepIncharge selectByAMIdAndStep(long amId, int step) {
        return approvalStepInchargeMapper.selectByAMIdAndStep(amId,step);
    }
}
