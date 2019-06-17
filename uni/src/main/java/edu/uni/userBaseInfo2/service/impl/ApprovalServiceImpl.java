package edu.uni.userBaseInfo2.service.impl;

import edu.uni.auth.bean.Role;
import edu.uni.auth.mapper.RoleMapper;
import edu.uni.auth.service.RoleService;
import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.mapper.ApprovalMainMapper;
import edu.uni.userBaseInfo2.mapper.UserMapper;
import edu.uni.userBaseInfo2.mapper.UserinfoApplyApprovalMapper;
import edu.uni.userBaseInfo2.mapper.UserinfoApplyMapper;
import edu.uni.userBaseInfo2.service.ApprovalService;
import edu.uni.userBaseInfo2.service.model.ApprovalModel;
import edu.uni.userBaseInfo2.service.model.ApprovalStepModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/16 14:41
 * @Version 1.0
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private UserinfoApplyApprovalMapper userinfoApplyApprovalMapper;
    @Autowired
    private UserinfoApplyMapper userinfoApplyMapper;
    @Autowired
    private ApprovalMainMapper approvalMainMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<ApprovalModel> select(long id, List<Role> roles) {

        //根据user_id查询user_role表找到role_id,根据role_id查找role表找到role_name
        List<String> roleNames = new ArrayList<>();
        for(int i=0;i<roles.size();i++){
            roleNames.add(roles.get(i).getName());
        }
        System.out.println("用户的角色s："+roleNames);
//        roleNames.add("3");   //班主任权限
//        roleNames.add("4"); //辅导员权限
//        roleNames.add("5"); //副书记权限
//        roleNames.add("6"); //书记权限
//        roleNames.add("7"); //副院长权限
//        roleNames.add("8"); //院长权限
//        roleNames.add("9"); //副处长权限
//        roleNames.add("10"); //处长权限

        //根据roleName和学校id查找userinfo_apply_approval表找到审批流程表
        long uniId = userMapper.selectUniIdById(id).getUniversityId();
        List<UserinfoApplyApproval> userinfoApplyApproval = userinfoApplyApprovalMapper.selectByRoleNameAndUniId(roleNames,uniId);
        List<ApprovalModel> approvalModels = new ArrayList<>();
        for(int i=0;i<userinfoApplyApproval.size();i++){
            ApprovalModel approvalModel = new ApprovalModel();
            UserinfoApply userinfoApply = userinfoApplyMapper.selectByPrimaryKey(userinfoApplyApproval.get(i).getUserinfoApplyId());
            ApprovalMain approvalMain = approvalMainMapper.selectByPrimaryKey(userinfoApply.getApprovalMainId());
            String approvalName = approvalMain.getName();
            approvalModel.setUserinfoApplyApprovalId(userinfoApplyApproval.get(i).getId());
            approvalModel.setApplyUserId(userinfoApply.getByWho());
            approvalModel.setApprovalName(approvalName);
            approvalModel.setReanson(userinfoApply.getApplyReason());
            if( userinfoApplyApproval.get(i).getDeleted() == true){
                approvalModel.setDeleted(1);    //deleted=1 既已审批
            } else {
                approvalModel.setDeleted(0);    //未审批
            }

            if(userinfoApplyApproval.get(i).getCheckWho() != null) {
                approvalModel.setCheckWho(userinfoApplyApproval.get(i).getCheckWho());
                approvalModel.setCheckTime(userinfoApplyApproval.get(i).getCheckTime());
                approvalModel.setReanson(userinfoApplyApproval.get(i).getReason());
                if(userinfoApplyApproval.get(i).getResult() == true){
                    approvalModel.setResult(1);
                } else {
                    approvalModel.setResult(2);
                }
            }
            int step = userinfoApplyApproval.get(i).getStep();
            approvalModel.setStep(step);
            approvalModel.setNewInfoId(userinfoApply.getNewInfoId());
            if(userinfoApplyApproval.get(i).getStep() != 1){
                approvalModel.setByWho(userinfoApplyApproval.get(i).getByWho());
            }
            approvalModel.setOldInfoId(userinfoApply.getOldInfoId());
            approvalModel.setStartTime(userinfoApply.getStartTime());
            //添加每一步的审批结果
            List<ApprovalStepModel> approvalStepModels = new ArrayList<>();
            int step_cnt = approvalMain.getStepCnt();
            if(step > 1) {
                for(int j=1;j<step;j++){    //不包括这一步的
                    ApprovalStepModel approvalStepModel = new ApprovalStepModel();
                    approvalStepModel.setStep(j);
                    //根据申请表id和step步数查找审批人
                    UserinfoApplyApproval userinfoApplyApproval1 = userinfoApplyApprovalMapper.selectByUAIdAndStep(userinfoApply.getId(),j);
                    long checkWho = userinfoApplyApproval1.getCheckWho();
                    String reason = userinfoApplyApproval1.getReason();
                    approvalStepModel.setCheck_who(checkWho);
                    if(userinfoApplyApproval1.getResult() == true){
                        approvalStepModel.setResult(1);
                    } else {
                        approvalStepModel.setResult(2);
                    }
                    approvalStepModel.setReason(reason);

                    approvalStepModels.add(approvalStepModel);
                }
            }
            approvalModel.setApprovalStepModels(approvalStepModels);
            approvalModels.add(approvalModel);
        }

        return approvalModels;
    }

    @Override
    public boolean update(ApprovalModel approvalModel){

        return false;
    }

}
