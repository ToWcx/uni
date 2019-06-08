package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.service.UserinfoApplyApprovalService;
import edu.uni.userBaseInfo2.service.UserinfoApplyService;
import edu.uni.userBaseInfo2.service.model.ApprovalStepModel;
import edu.uni.userBaseInfo2.service.model.UserinfoApplyResultModel;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/6/8 15:00
 * @Version 1.0
 */
@Api(description = "申请中心模块")
@Controller
@RequestMapping("/json/userBaseInfo2")
public class UserinfoApplyController {
    @Autowired
    private UserinfoApplyService userinfoApplyService;
    @Autowired
    private UserinfoApplyApprovalService userinfoApplyApprovalService;
    @Autowired
    private RedisCache cache;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // e_c_categories_listAll 模块名简称_操作对象_操作方法 （学生的不知道要不要和职员的分开）
        public static final String ListAll_CacheName = "ub2_s_address_listAll";
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_s_address_";
    }

    /**
     * 根据userId获取申请结果表
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据userId获取申请结果表", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/userinfoApply/{id}")
    public void receiveApplyResult(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<UserinfoApplyResultModel> userinfoApplyResultModels = new ArrayList<>();
        List<UserinfoApply> userinfoApplys = userinfoApplyService.selectByUserId(id);  //userId即表中的byWho字段
        //遍历全部申请表
        for(int x=0;x<userinfoApplys.size();x++){
            UserinfoApplyResultModel userinfoApplyResultModel = new UserinfoApplyResultModel();

            //插入申请表的基础信息
            userinfoApplyResultModel.setApplyReason(userinfoApplys.get(x).getApplyReason());
            if(userinfoApplys.get(x).getApplyResult() == true){
                userinfoApplyResultModel.setApplyResult(1); //1是通过 2是不通过 默认0是未审批
            } else {
                userinfoApplyResultModel.setApplyResult(2);
            }
            userinfoApplyResultModel.setStartTime(userinfoApplys.get(x).getStartTime());
            userinfoApplyResultModel.setEndTime(userinfoApplys.get(x).getEndTime());
            userinfoApplyResultModel.setNewInfoId(userinfoApplys.get(x).getNewInfoId());
            userinfoApplyResultModel.setOldInfoId(userinfoApplys.get(x).getOldInfoId());
            long uAid = userinfoApplys.get(x).getId();
            //插入申请表的审批流程信息
            List<UserinfoApplyApproval> userinfoApplyApprovals = userinfoApplyApprovalService.selectByUAId(uAid);
            List<ApprovalStepModel> approvalStepModels = new ArrayList<>();
            for(int i=0;i<userinfoApplyApprovals.size();i++){
                ApprovalStepModel approvalStepModel = new ApprovalStepModel();
                approvalStepModel.setReason(userinfoApplyApprovals.get(i).getReason());
                approvalStepModel.setCheck_who(userinfoApplyApprovals.get(i).getCheckWho());
                approvalStepModel.setStep(i);
                if(userinfoApplyApprovals.get(i).getResult() == true){
                    approvalStepModel.setResult(1); //1是通过 2是不通过 0是未审批
                } else if(userinfoApplyApprovals.get(i).getResult() == false){
                    approvalStepModel.setResult(2);
                } else {
                    System.out.println("result为空");
                    approvalStepModel.setResult(0);
                }
                approvalStepModels.add(approvalStepModel);
            }
            userinfoApplyResultModel.setApprovalStepModels(approvalStepModels);
            userinfoApplyResultModels.add(userinfoApplyResultModel);
        }

//        List<ApprovalModel> approvalModels = approvalService.select(id);
//        AddressVO addressVO = convertAddressFromModel(addressModel);
//        addressVO.setUserId(id);
        String json = Result.build(ResultType.Success).appendData("applyResultInfo", userinfoApplyResultModels ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }


}
