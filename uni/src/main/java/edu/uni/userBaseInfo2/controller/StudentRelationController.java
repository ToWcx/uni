package edu.uni.userBaseInfo2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.bean.ResultType;

import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.bean.StudentRelation;

import edu.uni.userBaseInfo2.controller.approvalUtil.StudentRelationAU;
import edu.uni.userBaseInfo2.controller.viewObject.AddressVO;
import edu.uni.userBaseInfo2.controller.viewObject.StudentRelationVO;

import edu.uni.userBaseInfo2.service.StudentRelationService;

import edu.uni.userBaseInfo2.service.UserService;
import edu.uni.userBaseInfo2.service.model.*;

import edu.uni.utils.RedisCache;
import edu.uni.bean.Result;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(description = "亲属查询模块")
@Controller
@RequestMapping("/json/userBaseInfo2/Relation")
public class StudentRelationController {

    @Autowired
    private StudentRelationService studentRelationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private RedisCache cache;


    static class CacheNameHelper{
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_studentRelation_";
        // e_c_categories_listAll
        public static final String ListAll_CacheName = "ub2_studentRelation_listAll";
    }
    /**
     * 新增亲属信息
     */
    @ApiOperation(value = "新增类别",notes = "未测试")
  //  @ApiImplicitParam(name = "StudentRelation",value = "类别详情实体",required = true,dataType = "studentRelation")
    @PostMapping("/createRelation")
    @ResponseBody
    public Result createStudentRelation(@RequestBody(required = false)StudentRelation studentRelation){
        if(studentRelation!=null){
            boolean success = studentRelationService.insert(studentRelation);
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
//    /**
//     * 删除
//     */
//    @ApiOperation(value = "删除类别",notes = "未测试")
//    @ApiImplicitParam(name = "id",value = "类别id",required = true,dataType = "Long",paramType = "path")
//    @DeleteMapping("/destoryRelation/{id}")
//    @ResponseBody
//    public Result destorystudentRelation(@PathVariable Long id) {
//        boolean success = studentRelationService.delete(id);
//        if (success) {
//            //清空相关缓存
//            cache.delete(CacheNameHelper.ListAll_CacheName);
//            return Result.build(ResultType.Success);
//        }else {
//            return Result.build(ResultType.Failed);
//        }
//    }

    @ApiOperation(value = "更新类别id更新类别信息",notes = "未测试")
    //@ApiImplicitParam(name = "studentRelation",value = "类别实体",required = true,dataType = "studentRelation")
    @PutMapping("/updateRelation")
    @ResponseBody
    public Result updateStudentRelation(@RequestBody(required = false)StudentRelation studentRelation){
        if(studentRelation!=null&&studentRelation.getId()!=null){
            boolean success = studentRelationService.update(studentRelation);
            if(success){
                cache.delete(CacheNameHelper.Receive_CacheNamePrefix);
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

//    @ApiOperation(value = "通过id查找Relation",notes = "未测试")
//    @ApiImplicitParam(name = "id",value = "类别id",required = true,dataType = "Long",paramType = "path")
//    @GetMapping("/studentRelation/{id}")
//    public void idStudentRelation (@PathVariable Long id, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset = utf-8");
//        String cacheName = CacheNameHelper.Receive_CacheNamePrefix+id;
//        // String json = cache.get(cacheName);
//        // if(json = null){
//            StudentRelationModel studentRelationModel = studentRelationService.select(id);
//            StudentRelationVO studentRelationVO =convertStudentRelationFromModel(studentRelationModel);
//
//            String json = Result.build(ResultType.Success).appendData("studentRelation",studentRelationVO).convertIntoJSON();
//
//        //  }
//        response.getWriter().write(json);
//    }

    /**
     * 根据亲属关系relation获取亲属信息
     * @param relation
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据亲属关系relation获取亲属信息", notes="未测试")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "relation", value = "亲属关系", required = false, dataType = "String", paramType = "query"),
    })
    @GetMapping("/StudentRelationByRela")
    public void RelationStudentRelation(@RequestParam("relation") String relation, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long userId = user.getId();

//        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + userId;
//         String json = cache.get(cacheName);
//         if(json = null){

        List<StudentRelationModel> studentRelationModels=studentRelationService.selectRela(userId,relation);
        for(int i=0;i<studentRelationModels.size();i++){
            long relaId = studentRelationModels.get(i).getRelaId();
            UserModel userModel = userService.select(relaId);
            System.out.println(userModel);
            BeanUtils.copyProperties(userModel,studentRelationModels.get(i));
            System.out.println(studentRelationModels.get(i));
        }
        String json = Result.build(ResultType.Success).appendData("studentRelation",studentRelationModels).convertIntoJSON();
//          }
        response.getWriter().write(json);
    }

    /**
     * 根据userId和亲属关系relation获取亲属信息
     * @param userId
     * @param relation
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据userId和亲属关系relation获取亲属信息", notes="未测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "relation", value = "亲属关系", required = false, dataType = "String", paramType = "query"),
    })
    @GetMapping("/StudentRelationByIdAndRela")
    public void RelationStudentRelation(@RequestParam("userId") Long userId,@RequestParam("relation") String relation, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
//        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + userId;
//         String json = cache.get(cacheName);
//         if(json = null){

            List<StudentRelationModel> studentRelationModels=studentRelationService.selectRela(userId,relation);
            for(int i=0;i<studentRelationModels.size();i++){
                long relaId = studentRelationModels.get(i).getRelaId();
                UserModel userModel = userService.select(relaId);
                System.out.println(userModel);
                BeanUtils.copyProperties(userModel,studentRelationModels.get(i));
                System.out.println(studentRelationModels.get(i));
            }
            String json = Result.build(ResultType.Success).appendData("studentRelation",studentRelationModels).convertIntoJSON();
//          }
        response.getWriter().write(json);
    }

    @ApiOperation(value="查找Relation",notes="未测试")
//    @ApiImplicitParam(name="userid",value="类别id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/StudentRelation")
    public void receiveStudentRelation (HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long userid = user.getId();
        String cacheName = CacheNameHelper.ListAll_CacheName+userid;
        List<StudentRelationModel> studentRelationModels=studentRelationService.selectUser(userid);
        for(int i=0;i<studentRelationModels.size();i++){
            long relaId = studentRelationModels.get(i).getRelaId();
            UserModel userModel = userService.select(relaId);
            System.out.println(userModel);
            BeanUtils.copyProperties(userModel,studentRelationModels.get(i));
            System.out.println(studentRelationModels.get(i));
        }
        StudentRelationVO studentRelationVO=convertStudentRelationModel(studentRelationModels);
        studentRelationVO.setUserId(userid);
        String json=Result.build(ResultType.Success).appendData("studentRelation",studentRelationVO).convertIntoJSON();
        response.getWriter().write(json);
    }

    @ApiOperation(value="通过userid查找Relation",notes="未测试")
    @ApiImplicitParam(name="userid",value="类别id",required = true,dataType = "Long",paramType = "path")
    @GetMapping("/StudentRelation/{userid}")
    public void receiveStudentRelationById (@PathVariable Long userid,HttpServletResponse response) throws IOException{
           response.setContentType("application/json;charset=utf-8");

           String cacheName = CacheNameHelper.ListAll_CacheName+userid;
           List<StudentRelationModel> studentRelationModels=studentRelationService.selectUser(userid);
           for(int i=0;i<studentRelationModels.size();i++){
               long relaId = studentRelationModels.get(i).getRelaId();
               UserModel userModel = userService.select(relaId);
               System.out.println(userModel);
               BeanUtils.copyProperties(userModel,studentRelationModels.get(i));
               System.out.println(studentRelationModels.get(i));
           }
           StudentRelationVO studentRelationVO=convertStudentRelationModel(studentRelationModels);
           studentRelationVO.setUserId(userid);
           String json=Result.build(ResultType.Success).appendData("studentRelation",studentRelationVO).convertIntoJSON();
           response.getWriter().write(json);
    }

    private StudentRelationVO convertStudentRelationFromModel(StudentRelationModel studentRelationModels){
        System.out.println(studentRelationModels);
        if(studentRelationModels == null){
            return null;
        }
        StudentRelationVO studentRelationVO = new StudentRelationVO();
        BeanUtils.copyProperties(studentRelationModels,studentRelationVO);
        return studentRelationVO;
    }

    private StudentRelationAU convertStudentRelation(List<StudentRelationModel> studentRelationModel){
        System.out.println(studentRelationModel);
        if(studentRelationModel==null){
            return null;
        }
        StudentRelationAU studentRelationAU=new StudentRelationAU();
        BeanUtils.copyProperties(studentRelationModel,studentRelationAU);
        return studentRelationAU;
    }


    private StudentRelationVO convertStudentRelationModel(List<StudentRelationModel> studentRelationModels){
        if(studentRelationModels == null){
            return null;
        }
        StudentRelationVO studentRelationVO = new StudentRelationVO();
        studentRelationVO.setStudentRelationModels(studentRelationModels);
        return studentRelationVO;
    }
}
