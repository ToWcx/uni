package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.mapper.UserinfoApplyMapper;
import edu.uni.userBaseInfo2.service.UserinfoApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author wuchuxin
 * @Date 2019/5/12 18:57
 * @Version 1.0
 */
@Service
public class UserinfoApplyServiceImpl implements UserinfoApplyService {

    @Autowired
    private UserinfoApplyMapper userinfoApplyMapper;

    /**
     * 插入用户申请信息
     * @param userinfoApply
     * @return
     */
    @Override
    public boolean insert(UserinfoApply userinfoApply) {
        return userinfoApplyMapper.insert(userinfoApply) > 0 ? true : false;
    }

    @Override
    public UserinfoApply select(long id) {
        return userinfoApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(UserinfoApply userinfoApply) {
        return userinfoApplyMapper.update(userinfoApply) > 0 ? true : false;
    }


//    /**
//     * 根据id查找审批业务id
//     * @param id
//     * @return
//     */
//    @Override
//    public UserinfoApply selectAMId(long id) {
//        return userinfoApplyMapper.selectAMId(id);
//    }
}
