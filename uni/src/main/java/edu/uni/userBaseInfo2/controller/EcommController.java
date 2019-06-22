package edu.uni.userBaseInfo2.controller;

import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.auth.service.RoleService;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.bean.Ecomm;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.controller.approvalUtil.EcommAU;
import edu.uni.userBaseInfo2.controller.viewObject.EcommVO;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.EcommModel;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(description = "用户通讯方式模块")
@Controller
@RequestMapping("/json/userBaseInfo2")    ///json/【模块名】/【操作对象】/ 选项 (如果有)
public class EcommController {

    @Autowired
    private EcommService ecommService;
    @Autowired
    private ApprovalMainService approvalMainService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserinfoApplyService userinfoApplyService;
    @Autowired
    private ApprovalStepInchargeService approvalStepInchargeService;
    @Autowired
    private UserinfoApplyApprovalService userinfoApplyApprovalService;
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
        // e_c_categories_listAll 模块名简称_操作对象_操作方法
        public static final String ListAll_CacheName = "ub2_s_ecomms_listAll";
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_s_ecomm_";

    }
    /**
     * 新增类别
     * @param ecomm
     * @return
     */
    @ApiOperation(value="新增类别", notes="未测试")
    @ApiImplicitParam(name = "ecomm", value = "类别详情实体", required = true, dataType = "Ecomm")
    @PostMapping("/ecomm")
    @ResponseBody
    public Result create(@RequestBody(required = false) Ecomm ecomm){
        User user = authService.getUser();
        if(user == null){
            return Result.build(ResultType.Failed,"你沒有登錄");
        }
        if(ecomm != null){
            long userId = user.getId();
            ecomm.setUserId(userId);
            ecomm.setByWho(userId);
            ecomm.setDeleted(false);
            ecomm.setDatetime(new Date());
            boolean success = ecommService.insert(ecomm);
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
//    @ApiOperation(value="删除类别", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "Integer", paramType = "path")
//    @DeleteMapping("/ecomm/{id}")
//    @ResponseBody
//    public Result destroy(@PathVariable Integer id){
//        boolean success = ecommService.delete(id);
//        if(success){
//            // 清空相关缓存
//            cache.delete(CacheNameHelper.ListAll_CacheName);
//            return Result.build(ResultType.Success);
//        }else{
//            return Result.build(ResultType.Failed);
//        }
//    }

    /**
     * 更新类别
     * @param ecommAU
     * @return
     */
    @Transactional
    @ApiOperation(value="用户申请修改通讯", notes="未测试")
    @ApiImplicitParam(name = "ecommAU", value = "类别实体", required = true, dataType = "EcommAU")
    @PutMapping("/Ecomm")
    @ResponseBody
    public Result updateStudent(@RequestBody(required = false) EcommAU ecommAU){
        User user = authService.getUser();
        if(user == null){
            return Result.build(ResultType.Failed,"你沒有登錄");
        }
        long userId = user.getId();
        // 审核的信息种类 0:联系方式  1:地址 2：照片  3：亲属  4：学历  5：简历
        // 6：学生信息 7：教职工信息 8：用户个人信息 9：学生excel表  10：职员excel表
        if(userinfoApplyService.selectByUserIdAndType(userId,0) != null){
            System.out.println("已有审批记录，请等审批结束后再提交修改");
            return Result.build(ResultType.Failed,"已有审批记录，请等审批结束后再提交修改");
        }

        if(ecommAU != null) {
            Ecomm ecomm = ecommAU.getEcomm();
            UserinfoApply userinfoApply = ecommAU.getUserinfoApply();
            Date date = new Date();
            ecomm.setUserId(userId);
            userinfoApply.setByWho(userId);
            ecomm.setDatetime(date);
            ecomm.setDeleted(true);  //改
            ecomm.setByWho(userinfoApply.getByWho());

            boolean isSuccess = ecommService.insert(ecomm);
            if (isSuccess == true) {    //插入成功
                System.out.println("插入新通讯方式成功");
                long aId = ecomm.getId(); //新纪录id
                userinfoApply.setNewInfoId(aId);
                userinfoApply.setStartTime(date);
                userinfoApply.setDatetime(date);
                //根据userId查到用户的学校id
                Long uniId = userService.selectUniIdById(userinfoApply.getByWho()).getUniversityId();
                //根据学校id和业务类型name="地址业务"找到唯一的步数规定表,获取该业务步数stepCnt以及该表id
                ApprovalMain approvalMain = null;
                if(ecommAU.getType() == 1){     //用户类型： 0:游客 1:学生  2:教职员工 3:校外职员  4:学生亲属  5:系统运营者  6:学校信息主管
                    approvalMain = approvalMainService.selectByUniIdAndName(uniId,"学生申请修改通讯");
                } else if(ecommAU.getType() == 2){
                    approvalMain = approvalMainService.selectByUniIdAndName(uniId,"职员申请修改通讯");
                }
                long AMId = approvalMain.getId();
                if(approvalMain == null){
                    System.out.println("approvalMain为空 查询不到该审批步数规定表");
                    return Result.build(ResultType.Failed);
                }
                userinfoApply.setApprovalMainId(AMId);
                userinfoApply.setInfoType(0);
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
                String roleName = roleService.selectRoleNameByRoleId(roleId);
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
                System.out.println("修改通讯信息失败 无法插入通讯");
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }



    /**
     * 根据id获取用户通讯地址信息
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取用户通讯地址类别详情", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "query")
    @GetMapping("/ecomm")
    public void receiveEcomm(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        System.out.println(id);
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<EcommModel> ecommModels = ecommService.selectAll(id);
        System.out.println(ecommService);
        EcommVO ecommVO = convertEcommFromModel(ecommModels);
        ecommVO.setUserId(id);
        System.out.println(ecommVO);
        String json = Result.build(ResultType.Success).appendData("ecomm", ecommVO).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 根据id获取用户通讯地址信息
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取用户通讯地址类别详情", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "query")
    @GetMapping("/ecomm/{id}")
    public void receiveEcommById(@RequestParam(required = false) Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
//            String json = Result.build(ResultType.Failed).appendData("login", "fail" ).convertIntoJSON();
//            response.getWriter().write(json);
            return;
        }
        if(id == null){
            id = user.getId();
        }
        System.out.println(id);
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<EcommModel> ecommModels = ecommService.selectAll(id);
        System.out.println(ecommService);
        EcommVO ecommVO = convertEcommFromModel(ecommModels);
        ecommVO.setUserId(id);
        System.out.println(ecommVO);
        String json = Result.build(ResultType.Success).appendData("ecomm", ecommVO).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    private EcommVO convertEcommFromModel(List<EcommModel> ecommModels){
        if(ecommModels == null){
            return null;
        }
        EcommVO ecommVO = new EcommVO();
        ecommVO.setEcomms(ecommModels);
        return ecommVO;
    }
}
