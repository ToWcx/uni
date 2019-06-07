package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.mapper.ApprovalMainMapper;
import edu.uni.userBaseInfo2.service.ApprovalMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/11 16:56
 * @Version 1.0
 */
@Service
public class ApprovalMainServiceImpl implements ApprovalMainService {

    @Autowired
    private ApprovalMainMapper approvalMainMapper;

    @Override
    public boolean insert(ApprovalMain approvalMain) {
        return approvalMainMapper.insert(approvalMain) > 0 ? true : false;
    }

    @Override
    public boolean deleteByUniIdAndName(Long universityId, String name) {
        return approvalMainMapper.deleteByUniIdAndName(universityId,name) > 0 ? true : false;
    }

    @Override
    public boolean update(ApprovalMain approvalMain) {
        return approvalMainMapper.updateByPrimaryKeySelective(approvalMain) > 0 ? true : false;
    }

    @Override
    public ApprovalMain select(long id) {
        return approvalMainMapper.selectByPrimaryKey(id);
    }

    @Override
    public ApprovalMain selectByUniIdAndName(Long universityId, String name){
        return approvalMainMapper.selectByUniIdAndName(universityId,name);
    }

    /**
     * 根据学校id 查找所有有效审批步数规定表
     * @param id
     * @return
     */
    @Override
    public List<ApprovalMain> selectByUniId(Long id) {
        return approvalMainMapper.selectByUniId(id);
    }

    /**
     * 查询所有有效的步骤规定类别
     * @return
     */
    @Override
    public List<ApprovalMain> selectAll() {
        return approvalMainMapper.selectAllByDelete();
    }
}
