package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.UserinfoApply;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/12 18:56
 * @Version 1.0
 */
public interface UserinfoApplyService {

    /**
     * 插入用户信息申请
     * @param userinfoApply
     * @return
     */
    boolean insert(UserinfoApply userinfoApply);

    /**
     * 根据id查找用户申请表记录
     * @param id
     * @return
     */
    UserinfoApply select(long id);

    /**
     * 根据byWho查找用户申请表记录
     * @param id
     * @return
     */
    List<UserinfoApply> selectByUserId(long id);

    /**
     * 更新用户申请表
     * @param userinfoApply
     * @return
     */
    boolean update(UserinfoApply userinfoApply);

//    /**
//     * 根据id查找审批业务id
//     * @param id
//     * @return
//     */
//    UserinfoApply selectAMId(long id);
}
