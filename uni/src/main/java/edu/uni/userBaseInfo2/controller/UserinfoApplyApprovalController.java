package edu.uni.userBaseInfo2.controller;

import edu.uni.auth.bean.Role;
import edu.uni.auth.bean.User;
import edu.uni.auth.bean.UserRole;
import edu.uni.auth.service.AuthService;
import edu.uni.auth.service.RoleService;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.*;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.ApprovalModel;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/15 22:06
 * @Version 1.0
 */
@Api(description = "审批中心模块")
@Controller
@RequestMapping("/json/userBaseInfo2")
public class UserinfoApplyApprovalController {
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private UserinfoApplyApprovalService userinfoApplyApprovalService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApprovalMainService approvalMainService;
    @Autowired
    private ApprovalStepInchargeService approvalStepInchargeService;
    @Autowired
    private UserinfoApplyService userinfoApplyService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private EcommService ecommService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LearningDegreeService learningDegreeService;
    @Autowired
    private EmployeeHistoryService employeeHistoryService;
    @Autowired
    private AuthService authService;
    @Autowired
    private RoleService roleService;
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
     * 获取审批表
     * @param response
     * @throws IOException
     */
    @Transactional
    @ApiOperation(value="获取审批表", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/userinfoApplyApproval")
    public void receiveApply(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        long uniId = userService.selectUniIdById(id).getUniversityId();
        List<Role> roles = roleService.selectByUidAndUniversityId(id, uniId);
        List<ApprovalModel> approvalModels = approvalService.select(id,roles);
//        AddressVO addressVO = convertAddressFromModel(addressModel);
//        addressVO.setUserId(id);
        String json = Result.build(ResultType.Success).appendData("approvalInfo", approvalModels ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }


    /**
     * 更新类别
     * @param approvalModel
     * @return
     */
    @Transactional
    @ApiOperation(value="审批申请信息", notes="未测试")
    @ApiImplicitParam(name = "approvalModel", value = "类别实体", required = true, dataType = "ApprovalModel")
    @PutMapping("/userinfoApplyApproval")
    @ResponseBody
    public Result update(@RequestBody(required = false) ApprovalModel approvalModel){
//        if(approvalModel != null && ecomm.getId() != null){
        User user = authService.getUser();
        if(user == null){
            return Result.build(ResultType.Failed, "你沒有登錄");
        }
        long userId = user.getId();
        approvalModel.setCheckWho(userId);
        if(approvalModel != null){
            long uAAId = approvalModel.getUserinfoApplyApprovalId();    //审批流程表id
            UserinfoApplyApproval userinfoApplyApproval = new UserinfoApplyApproval();
            int result = approvalModel.getResult();
            userinfoApplyApproval.setId(approvalModel.getUserinfoApplyApprovalId());
            userinfoApplyApproval.setReason(approvalModel.getReanson());
            if(result == 1){
                userinfoApplyApproval.setResult(true);
            }else if(result == 2){
                userinfoApplyApproval.setResult(false);
            }else{
                System.out.println("userinfoApplyApproval出错****************************");
                return null;
            }
//            userinfoApplyApproval.setResult(result);
            userinfoApplyApproval.setCheckWho(approvalModel.getCheckWho());
            Date date = new Date();
            userinfoApplyApproval.setCheckTime(date);

            boolean success = userinfoApplyApprovalService.update(userinfoApplyApproval);
            if(success){
                System.out.println("更新审批流程表成功");
//                cache.delete(CacheNameHelper.Receive_CacheNamePrefix + userinfoApplyApproval.getId());
//                cache.delete(CacheNameHelper.ListAll_CacheName);

                UserinfoApplyApproval uIApplyApproval = userinfoApplyApprovalService.select(uAAId);
                long uAId = uIApplyApproval.getUserinfoApplyId();   //用户申请表id
                UserinfoApply userinfoApply = userinfoApplyService.select(uAId);
                //继续下一步审批(如果存在下一步)
                int step = uIApplyApproval.getStep();
                step++;
                Long uniId = userService.selectUniIdById(approvalModel.getCheckWho()).getUniversityId();
                //判断审批信息类型
                long AMId = userinfoApply.getApprovalMainId(); //查找审批业务id
                //根据审批业务id找到唯一的步数规定表,获取该业务步数stepCnt
                ApprovalMain approvalMain = approvalMainService.select(AMId);
                String approvalMainName = approvalMain.getName();
                int stepCnt = approvalMain.getStepCnt();
                if(step > stepCnt && result == 1) {    //审批通过且审批结束
                    long oldId = 0;
                    if(userinfoApply.getOldInfoId() != null){
                        oldId = userinfoApply.getOldInfoId();
                    }
                    System.out.println("oldId: " + oldId);
                    long newId = userinfoApply.getNewInfoId();
                    if(approvalMainName.equals("学生申请修改地址") || approvalMainName.equals("职员申请修改地址")){

//                        //根据userId和flag唯一确定一条地址信息，若已存在该flag，还需把旧记录删除
//                        Address addr = addressService.selectByUserIdAndFlag(userId,address.getFlag());
//                        if(addr != null){   //若已存在地址信息 删除
                        if(oldId != 0){   //判断是否存在旧id 存在则删掉再插入
                            if(addressService.updateById(oldId) == true){
                                System.out.println("AddressController.update -> 删除已有地址记录成功");
                            }else {
                                System.out.println("AddressController.update -> 删除已有地址记录失败");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ResultType.Failed);
                            }
                        }
                        addressService.updateTrueById(newId);
                        System.out.println("更新"+approvalMainName+"地址信息成功");
                        Date endTime = new Date();
                        userinfoApply.setEndTime(endTime);
                        userinfoApply.setApplyResult(userinfoApplyApproval.getResult());
                        userinfoApply.setDeleted(true);
                        if(userinfoApplyService.update(userinfoApply) == true) {
                            System.out.println("更新用户信息表成功");
                        } else {
                            System.out.println("更新用户信息表失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ResultType.Failed);
                        }
                    } else if(approvalMainName.equals("学生申请修改通讯") || approvalMainName.equals("职员申请修改通讯")){
                        if(oldId != 0){   //判断是否存在旧id 存在则删掉再插入
                            if(ecommService.updateById(oldId) == true){
                                System.out.println("AddressController.update -> 删除已有通讯记录成功");
                            }else {
                                System.out.println("EcommController.update -> 删除已有通讯记录失败");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ResultType.Failed);
                            }
                        }
                        ecommService.updateTrueById(newId);
                        System.out.println("更新"+approvalMainName+"通讯信息成功");
                        Date endTime = new Date();
                        userinfoApply.setEndTime(endTime);
                        userinfoApply.setApplyResult(userinfoApplyApproval.getResult());
                        userinfoApply.setDeleted(true);
                        if(userinfoApplyService.update(userinfoApply) == true) {
                            System.out.println("更新用户信息表成功");
                        } else {
                            System.out.println("更新用户信息表失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ResultType.Failed);
                        }
                    } else if(approvalMainName.equals("辅导员申请修改学生信息")){
                        if(oldId != 0){   //判断是否存在旧id 存在则删掉再插入
                            if(studentService.updateById(oldId) == true){
                                System.out.println("studentController.update -> 删除已有学生记录成功");
                            }else {
                                System.out.println("EcommController.update -> 删除已有学生记录失败");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ResultType.Failed);
                            }
                        } else{
                            studentService.select(userId);
                        }
                        studentService.updateTrueById(newId);
                        System.out.println("更新学生信息成功");
                        Date endTime = new Date();
                        userinfoApply.setEndTime(endTime);
                        userinfoApply.setApplyResult(userinfoApplyApproval.getResult());
                        userinfoApply.setDeleted(true);
                        if(userinfoApplyService.update(userinfoApply) == true) {
                            System.out.println("更新用户信息表成功");
                        } else {
                            System.out.println("更新用户信息表失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ResultType.Failed);
                        }
                    } else if(approvalMainName.equals("人事处申请修改职员学历")){
                        if(oldId != 0){   //判断是否存在旧id 存在则删掉再插入
                            if(learningDegreeService.updateById(oldId) == true){
                                System.out.println("studentController.update -> 删除已有学历记录成功");
                            }else {
                                System.out.println("EcommController.update -> 删除已有学历记录失败");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ResultType.Failed);
                            }
                        }
                        learningDegreeService.updateTrueById(newId);
                        System.out.println("更新学历信息成功");
                        Date endTime = new Date();
                        userinfoApply.setEndTime(endTime);
                        userinfoApply.setApplyResult(userinfoApplyApproval.getResult());
                        userinfoApply.setDeleted(true);
                        if(userinfoApplyService.update(userinfoApply) == true) {
                            System.out.println("更新用户信息表成功");
                        } else {
                            System.out.println("更新用户信息表失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ResultType.Failed);
                        }
                    } else if(approvalMainName.equals("人事处申请修改职员简历")){
                        if(oldId != 0){   //判断是否存在旧id 存在则删掉再插入
                            if(employeeHistoryService.updateById(oldId) == true){
                                System.out.println("employeeHistoryController.update -> 删除已有简历记录成功");
                            }else {
                                System.out.println("EcommController.update -> 删除已有简历记录失败");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ResultType.Failed);
                            }
                        }
                        employeeHistoryService.updateTrueById(newId);
                        System.out.println("更新简历信息成功");
                        Date endTime = new Date();
                        userinfoApply.setEndTime(endTime);
                        userinfoApply.setApplyResult(userinfoApplyApproval.getResult());
                        userinfoApply.setDeleted(true);
                        if(userinfoApplyService.update(userinfoApply) == true) {
                            System.out.println("更新用户信息表成功");
                        } else {
                            System.out.println("更新用户信息表失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ResultType.Failed);
                        }
                    } else if(approvalMainName.equals("人事处申请修改职员信息")){
                        if(oldId != 0){   //判断是否存在旧id 存在则删掉再插入
                            if(employeeService.updateById(oldId) == true){
                                System.out.println("studentController.update -> 删除已有职员记录成功");
                            }else {
                                System.out.println("EcommController.update -> 删除已有职员记录失败");
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return Result.build(ResultType.Failed);
                            }
                        }
                        employeeService.updateTrueById(newId);
                        System.out.println("更新职员信息成功");
                        Date endTime = new Date();
                        userinfoApply.setEndTime(endTime);
                        userinfoApply.setApplyResult(userinfoApplyApproval.getResult());
                        userinfoApply.setDeleted(true);
                        if(userinfoApplyService.update(userinfoApply) == true) {
                            System.out.println("更新用户信息表成功");
                        } else {
                            System.out.println("更新用户信息表失败");
                            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                            return Result.build(ResultType.Failed);
                        }
                    } else {
                        System.out.println("申请修改其他类型 暂无其他类型代码 审批结束");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ResultType.Failed);
                    }
                } else if(step <= stepCnt && result == 1) {  //审批通过且审批未结束
                    long roleId = approvalStepInchargeService.selectByAMIdAndStep(AMId,step).getRoleId();
                    //获取角色
                    //根据roleId获取roleName
                    String roleName = roleService.selectRoleNameByRoleId(roleId);
                    System.out.println("roleName"+roleName);
//                    String roleName = roleId+"";
                    UserinfoApplyApproval userinfoApplyApproval1 = new UserinfoApplyApproval();
                    userinfoApplyApproval1.setUserinfoApplyId(uIApplyApproval.getUserinfoApplyId());   //申请表id
                    userinfoApplyApproval1.setStep(step);
                    userinfoApplyApproval1.setRoleName(roleName);
                    userinfoApplyApproval1.setInfoType(uIApplyApproval.getInfoType());
                    userinfoApplyApproval1.setApplyUserId(userId);
                    userinfoApplyApproval1.setDatetime(date);
                    userinfoApplyApproval1.setByWho(uIApplyApproval.getCheckWho());    //上个审批人
                    userinfoApplyApproval1.setDeleted(false);
                    userinfoApplyApproval1.setUniversityId(uIApplyApproval.getUniversityId());
                    boolean success1 = userinfoApplyApprovalService.insert(userinfoApplyApproval1);
                    if(!success1){
                        System.out.println("插入审批表错误 userinfoApplyApprovalController Lin148");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ResultType.Failed);
                    }
                } else if(result == 2){ //审批不通过
                    Date endTime = new Date();
                    userinfoApply.setEndTime(endTime);
                        userinfoApply.setApplyResult(userinfoApplyApproval.getResult());
                        userinfoApply.setDeleted(true);
                    if(userinfoApplyService.update(userinfoApply) == true) {
                        System.out.println("更新用户信息表成功");
                    } else {
                        System.out.println("更新用户信息表失败");
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return Result.build(ResultType.Failed);
                    }
                } else {
                    System.out.println("UserinfoApplyApprovalController Update方法出错 line186");
                }
                return Result.build(ResultType.Success);
            }else{
                System.out.println("更新审批流程表失败");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }


}
