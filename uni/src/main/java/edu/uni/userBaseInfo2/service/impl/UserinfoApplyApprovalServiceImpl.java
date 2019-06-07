package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.mapper.UserinfoApplyApprovalMapper;
import edu.uni.userBaseInfo2.service.UserinfoApplyApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/15 21:08
 * @Version 1.0
 */
@Service
public class UserinfoApplyApprovalServiceImpl implements UserinfoApplyApprovalService {

    @Autowired
    private UserinfoApplyApprovalMapper userinfoApplyApprovalMapper;

    @Override
    public boolean insert(UserinfoApplyApproval userinfoApplyApproval) {
        return userinfoApplyApprovalMapper.insert(userinfoApplyApproval) > 0 ? true : false;
    }

    @Override
    public boolean update(UserinfoApplyApproval userinfoApplyApproval) {
        return userinfoApplyApprovalMapper.updateById(userinfoApplyApproval) > 0 ? true : false;
    }

    @Override
    public UserinfoApplyApproval select(long id) {
        return userinfoApplyApprovalMapper.selectByPrimaryKey(id);
    }

//    @Override
//    public UserinfoApplyApproval selectByUniIdAndName(Long universityId, String name) {
//        return null;
//    }
//
//    @Override
//    public List<UserinfoApplyApproval> selectByUniId(Long id) {
//        return null;
//    }
//
//    @Override
//    public List<UserinfoApplyApproval> selectAll() {
//        return null;
//    }
}
