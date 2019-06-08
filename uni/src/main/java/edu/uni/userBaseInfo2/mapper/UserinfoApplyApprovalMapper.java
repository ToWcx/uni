package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserinfoApplyApprovalMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserinfoApplyApproval record);

    int insertSelective(UserinfoApplyApproval record);

    UserinfoApplyApproval selectByPrimaryKey(Long id);

    //根据角色名和学校id查找用户信息审批流程表
    List<UserinfoApplyApproval> selectByRoleNameAndUniId(@Param("roleNames") List<String> roleNames, @Param("uniId") Long uniId);
    //根据申请表id和步数step查找审批人id
    UserinfoApplyApproval selectByUAIdAndStep(@Param("uAId") long uAId, @Param("step") int step);
    //根据申请表id查找审批流程表
    List<UserinfoApplyApproval> selectByUAId(long id);
    //根据id更新审批结果审批人审批时间审批理由 并将审批表置为已审批(deleted=1)
    int updateById(UserinfoApplyApproval userinfoApplyApproval);

    int updateByPrimaryKeySelective(UserinfoApplyApproval record);

    int updateByPrimaryKey(UserinfoApplyApproval record);
}