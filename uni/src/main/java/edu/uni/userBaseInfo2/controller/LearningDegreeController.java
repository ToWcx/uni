package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.bean.LearningDegree;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.controller.approvalUtil.LearningDegreeAU;
import edu.uni.userBaseInfo2.controller.viewObject.LearningDegreeVO;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.LearningDegreeModel;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Api(description = "用户学历模块")
@Controller
@RequestMapping("/json/userBaseInfo2/learningDegree")    ///json/【模块名】/【操作对象】/ 选项 (如果有)
public class LearningDegreeController {

    @Autowired
    private LearningDegreeService learningDegreeService;
    @Autowired
    private UserService userService;
    @Autowired
    private AcademicService academicService;
    @Autowired
    private AcademicDegreeService academicDegreeService;
    @Autowired
    private AddrCountryService addrCountryService;
    @Autowired
    private AddrCityService addrCityService;
    @Autowired
    private ApprovalMainService approvalMainService;
    @Autowired
    private UserinfoApplyService userinfoApplyService;
    @Autowired
    private ApprovalStepInchargeService approvalStepInchargeService;
    @Autowired
    private UserinfoApplyApprovalService userinfoApplyApprovalService;
    @Autowired
    private RedisCache cache;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // e_c_categories_listAll 模块名简称_操作对象_操作方法 （学生的不知道要不要和职员的分开）
        public static final String ListAll_CacheName = "ub2_s_learningDegree_listAll";
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_s_learningDegree_";
    }
    /**
     * 新增类别
     * @param learningDegree
     * @return
     */
    @ApiOperation(value="新增类别", notes="已测试")
    @ApiImplicitParam(name = "learningDegree", value = "类别详情实体", required = true, dataType = "LearningDegree")
    @PostMapping("/learningDegree")
    @ResponseBody
    public Result create(@RequestBody(required = false) LearningDegree learningDegree){
        if(learningDegree != null){
            boolean success = learningDegreeService.insert(learningDegree);
            if(success){
                // 清空相关缓存
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }
//    /**
//     * 删除类别
//     * @param id
//     * @return
//     */
//    @ApiOperation(value="删除类别", notes="已测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "Integer", paramType = "path")
//    @DeleteMapping("/learningDegree/{id}")
//    @ResponseBody
//    public Result destroy(@PathVariable Integer id){
//        boolean success = learningDegreeService.delete(id);
//        if(success){
//            // 清空相关缓存
//            cache.delete(AddressController.CacheNameHelper.ListAll_CacheName);
//            return Result.build(ResultType.Success);
//        }else{
//            return Result.build(ResultType.Failed);
//        }
//    }
    /**
     * 更新类别
     * @param learningDegreeAU
     * @return
     */
    @Transactional
    @ApiOperation(value="用户申请修改学历", notes="未测试")
    @ApiImplicitParam(name = "learningDegreeAU", value = "类别实体", required = true, dataType = "LearningDegreeAU")
    @PutMapping("/learningDegree")
    @ResponseBody
    public Result updateStudent(@RequestBody(required = false) LearningDegreeAU learningDegreeAU){
        if(learningDegreeAU != null) {
            LearningDegreeModel learningDegreeModel = learningDegreeAU.getLearningDegreeModel();
            LearningDegree learningDegree = convertBeanFromModel(learningDegreeModel);
            UserinfoApply userinfoApply = learningDegreeAU.getUserinfoApply();
            Date date = new Date();
            long userId = userinfoApply.getByWho();
            learningDegree.setUserId(userId);
            learningDegree.setDatetime(date);
            learningDegree.setDeleted(true);  //改
            learningDegree.setByWho(userId);

            boolean isSuccess = learningDegreeService.insert(learningDegree);
            if (isSuccess == true) {    //插入成功
                System.out.println("插入新学历成功");
                long aId = learningDegree.getId(); //新纪录id
                userinfoApply.setNewInfoId(aId);
                userinfoApply.setStartTime(date);
                userinfoApply.setDatetime(date);
                //根据userId查到用户的学校id
                Long uniId = userService.selectUniIdById(userinfoApply.getByWho()).getUniversityId();
                //根据学校id和业务类型name="地址业务"找到唯一的步数规定表,获取该业务步数stepCnt以及该表id
                ApprovalMain approvalMain = approvalMainService.selectByUniIdAndName(uniId,"人事处申请修改职员学历");
                long AMId = approvalMain.getId();
                if(approvalMain == null){
                    System.out.println("approvalMain为空 查询不到该审批步数规定表");
                    return Result.build(ResultType.Failed);
                }
                userinfoApply.setApprovalMainId(AMId);
                userinfoApply.setInfoType(4);
                userinfoApply.setUniversityId(uniId);
                userinfoApply.setDeleted(false);
                //把userinfoApply插入到数据库
                boolean uiASuccess = userinfoApplyService.insert(userinfoApply);
                if(uiASuccess == true){
                    System.out.println("userinfoApply插入成功");
                }else {
                    System.out.println("userinfoApply插入失败");
                    return Result.build(ResultType.Failed);
                }
                int step = 1;
                //缺少接口
                long roleId = approvalStepInchargeService.selectByAMIdAndStep(AMId,step).getRoleId();
                //获取角色
                //根据roleId获取roleName
                String roleName = roleId+"";
                UserinfoApplyApproval userinfoApplyApproval = new UserinfoApplyApproval();
                userinfoApplyApproval.setUniversityId(uniId);
                userinfoApplyApproval.setUserinfoApplyId(userinfoApply.getId());
                userinfoApplyApproval.setStep(step);
                userinfoApplyApproval.setRoleName(roleName);
                userinfoApplyApproval.setInfoType(userinfoApply.getInfoType());
                userinfoApplyApproval.setApplyUserId(userId);
                userinfoApplyApproval.setDatetime(date);
                userinfoApplyApproval.setByWho(userId);
                userinfoApplyApproval.setDeleted(false);

                boolean success = userinfoApplyApprovalService.insert(userinfoApplyApproval);
                if (success) {
                    //什么时候删除缓存？ 成功审批后吗？  放到最后一步再删除？
//                        cache.delete(CacheNameHelper.Receive_CacheNamePrefix + ecomm.getId());
//                        cache.delete(CacheNameHelper.ListAll_CacheName);
                    System.out.println("插入审批流程表成功");
                    return Result.build(ResultType.Success);
                } else {
                    return Result.build(ResultType.Failed);
                }

            }else {
                System.out.println("修改学历信息失败 无法插入通讯");
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 根据id获取用户学历信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取用户地址类别详情", notes="已测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/learningDegree/{id}")
    public void receiveLearningDegree(@PathVariable Long id, HttpServletResponse response)throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = LearningDegreeController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<LearningDegreeModel> learningDegreeModels = learningDegreeService.selectAll(id);
        LearningDegreeVO learningDegreeVO = convertLearningDegreeFromModel(learningDegreeModels);
        learningDegreeVO.setUserId(id);
        System.out.println(learningDegreeVO);
        String json = Result.build(ResultType.Success).appendData("learningDegree", learningDegreeVO).convertIntoJSON();
        //cache.set(cacheName,json);
        //}
        response.getWriter().write(json);
    }

    private LearningDegreeVO convertLearningDegreeFromModel(List<LearningDegreeModel> learningDegreeModels){
        if(learningDegreeModels == null){
            return null;
        }
        LearningDegreeVO learningDegreeVO = new LearningDegreeVO();
        learningDegreeVO.setLearningDegreeModels(learningDegreeModels);
        return learningDegreeVO;
    }

    private LearningDegree convertBeanFromModel(LearningDegreeModel learningDegreeModel){
        if(learningDegreeModel == null){
            return null;
        }
        LearningDegree learningDegree = new LearningDegree();
        BeanUtils.copyProperties(learningDegreeModel,learningDegree);
        learningDegree.setAcademicId(academicService.selectByName(learningDegreeModel.getAcademic()).getId());
        learningDegree.setDegreeId(academicDegreeService.selectByName(learningDegreeModel.getDegree()).getId());
        learningDegree.setCityId(addrCityService.selectByZh(learningDegreeModel.getCity()).getId());
        learningDegree.setCountryId(addrCountryService.selectByZh(learningDegreeModel.getCountry()).getId());
        learningDegree.setSchoolId((long)1);    //缺少接口
        return learningDegree;
    }
}
