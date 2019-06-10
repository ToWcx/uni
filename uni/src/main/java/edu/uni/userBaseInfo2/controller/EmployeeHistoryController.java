package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.bean.EmployeeHistory;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.controller.approvalUtil.EmployeeHistoryAU;
import edu.uni.userBaseInfo2.controller.viewObject.EmployeeHistoryVO;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.EmployeeHistoryModel;
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

@Api(description = "教职工简历模块")
@Controller
@RequestMapping("/json/userBaseInfo2")
public class EmployeeHistoryController {
    @Autowired
    private EmployeeHistoryService employeeHistoryService;
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
    private RedisCache cache;
    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_employeeHistory_";
        // e_c_categories_listAll
        public static final String ListAll_CacheName = "ub2_employeeHistory_listAll";
    }

    /**
     * 新增教职工简历
     * @param employeeHistory
     * @return
     */
    @ApiOperation(value = "新增类别",notes = "已测试")
    @ApiImplicitParam(name = "employeeHistory",value = "类别详情实体",required = true,dataType = "EmployeeHistory")
    @PostMapping("/employeeHistory")
    @ResponseBody
    public Result addEmployeeHistory(@RequestBody(required = false) EmployeeHistory employeeHistory){
        if(employeeHistory!=null){
            boolean success = employeeHistoryService.insert(employeeHistory);
            if(success){
                //清空相关缓存
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 更新类别
     * @param employeeHistoryAU
     * @return
     */
    @Transactional
    @ApiOperation(value="职员申请修改简历", notes="未测试")
    @ApiImplicitParam(name = "employeeHistoryAU", value = "类别实体", required = true, dataType = "EmployeeHistoryAU")
    @PutMapping("/employeeHistory")
    @ResponseBody
    public Result updateEmployeeHistory(@RequestBody(required = false) EmployeeHistoryAU employeeHistoryAU){
        if(employeeHistoryAU != null) {
            EmployeeHistoryModel employeeHistoryModel = employeeHistoryAU.getEmployeeHistoryModel();
            EmployeeHistory employeeHistory = new EmployeeHistory();
            BeanUtils.copyProperties(employeeHistoryModel,employeeHistory);
            UserinfoApply userinfoApply = employeeHistoryAU.getUserinfoApply();
            Date date = new Date();
            long userId = userinfoApply.getByWho();
            employeeHistory.setId(null);
            employeeHistory.setUserId(userId);
            employeeHistory.setDatetime(date);
            employeeHistory.setDeleted(true);  //改
            employeeHistory.setByWho(userId);

            boolean isSuccess = employeeHistoryService.insert(employeeHistory);
            if (isSuccess == true) {    //插入成功
                System.out.println("插入新简历成功");
                long aId = employeeHistory.getId(); //新纪录id
                userinfoApply.setNewInfoId(aId);
                userinfoApply.setStartTime(date);
                userinfoApply.setDatetime(date);
                //根据userId查到用户的学校id
                Long uniId = userService.selectUniIdById(userinfoApply.getByWho()).getUniversityId();
                //根据学校id和业务类型name="地址业务"找到唯一的步数规定表,获取该业务步数stepCnt以及该表id
                ApprovalMain approvalMain = approvalMainService.selectByUniIdAndName(uniId,"人事处申请修改职员简历");
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
                System.out.println("修改简历信息失败 无法插入简历");
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 删除教职工简历类别
     * @param id
     * @return
     */
    @ApiOperation(value = "删除类别",notes = "已测试")
    @ApiImplicitParam(name = "id",value = "类别id",required = true,dataType = "Long",paramType = "path")
    @DeleteMapping("/employeeHistory/{id}")
    @ResponseBody
    public Result destoryEmployeeHistory(@PathVariable Long id) {
        boolean success = employeeHistoryService.delete(id);
        if (success) {
            //清空相关缓存
            cache.delete(CacheNameHelper.ListAll_CacheName);
            return Result.build(ResultType.Success);
        }else {
            return Result.build(ResultType.Failed);
        }
    }

    /**
     * 根据id查找某个教职工的所有简历信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "根据类别id获取类别详情",notes = "已测试")
    @ApiImplicitParam(name = "id",value = "类别id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/employeeHistory/{id}")
    public void receiveEmployeeHistory (@PathVariable Long id,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset = utf-8");
        String cacheName = EmployeeHistoryController.CacheNameHelper.Receive_CacheNamePrefix+id;
        // String json = cache.get(cacheName);
        // if(json = null){
        List<EmployeeHistoryModel> employeeHistoryModels = employeeHistoryService.selectAll(id);
        EmployeeHistoryVO employeeHistoryVO =convertEmployeeHistoryFromModel(employeeHistoryModels);
        employeeHistoryVO.setUserId(id);
        System.out.println("HistoryController---"+employeeHistoryVO);
        String json = Result.build(ResultType.Success).appendData("employeeHistory",employeeHistoryVO).convertIntoJSON();
        //  }
        response.getWriter().write(json);
    }

    //    /**
//     * 列举所有类别
//     * @param response
//     * @throws IOException
//     */
//    @ApiOperation(value = "列举所有类别",notes = "已测试")
//    @GetMapping(value = "/employeeHistory/listAll")
//    public void list(HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset=utf-8");
//        String cacheName = CacheNameHelper.ListAll_CacheName;
//        //String json = cache.get(cacheName);
//       // if(json==null){
//          String  json = Result.build(ResultType.Success).appendData("employee",employeeHistoryService.selectAll()).convertIntoJSON();
//            cache.set(cacheName,json);
//      //  }
//        response.getWriter().write(json);
//    }

    private EmployeeHistoryVO convertEmployeeHistoryFromModel(List<EmployeeHistoryModel> employeeHistoryModel){
        System.out.println(employeeHistoryModel);
        if(employeeHistoryModel == null){
            return null;
        }
        EmployeeHistoryVO employeeHistoryVO = new EmployeeHistoryVO();
        employeeHistoryVO.setEmployeeHistoryModel(employeeHistoryModel);
        return employeeHistoryVO;
    }
}
