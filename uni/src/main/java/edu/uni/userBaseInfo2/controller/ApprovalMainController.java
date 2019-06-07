package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.controller.viewObject.ApprovalMainVO;
import edu.uni.userBaseInfo2.service.ApprovalMainService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/11 18:58
 * @Version 1.0
 */
@Api(description = "审批步数规定模块")
@Controller
@RequestMapping("/json/userBaseInfo2")
public class ApprovalMainController {

    @Autowired
    private ApprovalMainService approvalMainService;
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
     * 新增类别
     * @param approvalMain
     * @return
     */
    @ApiOperation(value="新增类别", notes="已测试")
    @ApiImplicitParam(name = "approvalMain", value = "类别详情实体", required = true, dataType = "ApprovalMain")
    @PostMapping("/approvalMain")
    @ResponseBody
    public Result create(@RequestBody(required = false) ApprovalMain approvalMain) {
        if (approvalMain != null) {
            //判断新增步数规定是否已存在
            if (approvalMainService.selectByUniIdAndName(approvalMain.getUniversityId(), approvalMain.getName()) == null) {
                Date now = new Date();
                approvalMain.setDatetime(now);
                approvalMain.setDeleted(false);

            boolean success = approvalMainService.insert(approvalMain);
            if (success) {
                // 清空相关缓存
                System.out.println("新增审批业务成功");
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            } else {
                System.out.println("新增审批业务失败");
                return Result.build(ResultType.Failed);
            }
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 删除类别
     * @param id
     * @return
     */
    @ApiOperation(value="删除类别", notes="已测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "Integer", paramType = "path")
    @DeleteMapping("/approvalMain/{id}")
    @ResponseBody
    public Result destroy(@PathVariable Integer id){
        Long universityId = approvalMainService.select(id).getUniversityId();
        String name = approvalMainService.select(id).getName();
        boolean success = approvalMainService.deleteByUniIdAndName(universityId,name);
        if(success){
            // 清空相关缓存
            cache.delete(CacheNameHelper.ListAll_CacheName);
            return Result.build(ResultType.Success);
        }else{
            return Result.build(ResultType.Failed);
        }
    }

    /**
     * 根据学校id获取审批步数规定类别
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据学校id获取审批步数规定类别", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/approvalMain/{id}")
    public void receiveApprovalMain(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        //传uniId过去 查找所有有效审批步数规定表
        List<ApprovalMain> approvalMains = approvalMainService.selectByUniId(id);
        ApprovalMainVO approvalMainVO = convertApprovalMainFromModel(approvalMains);
        approvalMainVO.setUniversityId(id);
        String json = Result.build(ResultType.Success).appendData("approvalMain", approvalMainVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    //修改

    private ApprovalMainVO convertApprovalMainFromModel(List<ApprovalMain> approvalMains){
        if(approvalMains == null) {
            return null;
        }
        ApprovalMainVO approvalMainVO = new ApprovalMainVO();
        approvalMainVO.setApprovalMainList(approvalMains);
        return approvalMainVO;
    }

}
