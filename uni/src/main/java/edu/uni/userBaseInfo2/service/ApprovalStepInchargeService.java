package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.ApprovalStepIncharge;
import org.apache.ibatis.annotations.Param;

/**
 * @Author wuchuxin
 * @Date 2019/5/14 21:14
 * @Version 1.0
 */
public interface ApprovalStepInchargeService {

    /**
     * 根据审批步数表id查找审批步骤详情表(step、roleId)
     * @param amId
     * @return
     */
    ApprovalStepIncharge selectByAMIdAndStep(long amId, int step);

}
