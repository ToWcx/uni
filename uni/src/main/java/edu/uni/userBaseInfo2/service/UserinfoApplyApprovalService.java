package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/12 14:44
 * @Version 1.0
 */
public interface UserinfoApplyApprovalService {
    /**
     * 保存新类别
     * @param userinfoApplyApproval
     * @return
     */
    boolean insert(UserinfoApplyApproval userinfoApplyApproval);

    /**
     * 更新类别
     * @param userinfoApplyApproval
     * @return
     */
    boolean update(UserinfoApplyApproval userinfoApplyApproval);

    /**
     * 查找类别
     * @param id
     * @return
     */
    UserinfoApplyApproval select(long id);
//
//    /**
//     *
//     * @param universityId
//     * @param name
//     * @return
//     */
//    UserinfoApplyApproval selectByUniIdAndName(Long universityId, String name);
//
//    /**
//     * 根据学校id查找所有有效的审批步数规定表
//     * @param id
//     * @return
//     */
//    List<UserinfoApplyApproval> selectByUniId(Long id);
//
//    /**
//     * 查找所有类别
//     * @return
//     */
//    List<UserinfoApplyApproval> selectAll();

}
