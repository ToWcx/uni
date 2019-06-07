package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.service.model.ApprovalModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/16 9:08
 * @Version 1.0
 */
public interface ApprovalService {

    List<ApprovalModel> select(long id);

    boolean update(ApprovalModel approvalModel);

}
