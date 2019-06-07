package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.service.model.ApprovalMainModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/11 16:15
 * @Version 1.0
 */
public interface ApprovalMainService {
    /**
     * 保存新类别
     * @param approvalMain
     * @return
     */
    boolean insert(ApprovalMain approvalMain);

    /**
     * 删除类别
     * @param universityId
     * @param name
     * @return
     */
    boolean deleteByUniIdAndName(Long universityId, String name);

    /**
     * 更新类别
     * @param approvalMain
     * @return
     */
    boolean update(ApprovalMain approvalMain);

    /**
     * 查找类别
     * @param id
     * @return
     */
    ApprovalMain select(long id);

    ApprovalMain selectByUniIdAndName(Long universityId, String name);

    /**
     * 根据学校id查找所有有效的审批步数规定表
     * @param id
     * @return
     */
    List<ApprovalMain> selectByUniId(Long id);

    /**
     * 查找所有类别
     * @return
     */
    List<ApprovalMain> selectAll();
}
