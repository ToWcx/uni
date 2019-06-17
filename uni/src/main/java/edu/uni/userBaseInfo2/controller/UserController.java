package edu.uni.userBaseInfo2.controller;

import edu.uni.auth.service.AuthService;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.User;
import edu.uni.userBaseInfo2.service.UserService;
import edu.uni.userBaseInfo2.service.model.UserModel;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(description = "用户基础信息模块")
@Controller
@RequestMapping("/json/userBaseInfo2/User")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
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
     * @param
     * @return
     */
    @ApiOperation(value="新增类别", notes="未测试")
    @ApiImplicitParam(name = "user", value = "类别详情实体", required = true, dataType = "User")
    @PostMapping("/create")
    @ResponseBody
    public Result create(@RequestBody(required = false) User user){
        if(user != null){
            boolean success = userService.insert(user);
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
//     * @param
//     * @return
//     */
//       @ApiOperation(value="删除类别", notes="未测试")
//      @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "Long", paramType = "path")
//     @DeleteMapping("/delete/{id}")
//    @ResponseBody
//    public Result destroy(@PathVariable Long id){
//        boolean success = userService.delete(id);
//        if(success){
//            // 清空相关缓存
//            cache.delete(CacheNameHelper.ListAll_CacheName);
//            return Result.build(ResultType.Success);
//        }else{
//            return Result.build(ResultType.Failed);
//        }
//    }

    @ApiOperation(value="游客修改个人信息", notes="未测试")
    @ApiImplicitParam(name = "user", value = "类别实体", required = true, dataType = "User")
    @PutMapping("/update")
    @ResponseBody
    public Result update(@RequestBody(required = false)User user){
        if(user != null && user.getId() != null){
            boolean success =userService.update(user);
            if(success){
                cache.delete(CacheNameHelper.Receive_CacheNamePrefix + user.getId());
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 获取学生基础信息
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="获取学生基础信息类别详情", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/UserBase")
    public void receiveBase(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        edu.uni.auth.bean.User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        UserModel userModel = userService.select(id);
        System.out.println(userModel);
        String json = Result.build(ResultType.Success).appendData("Userbase",userModel).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 根据id获取学生基础信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取学生基础信息类别详情", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/UserBase/{id}")
    public void receiveBase(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        UserModel userModel = userService.select(id);
        System.out.println(userModel);
        String json = Result.build(ResultType.Success).appendData("Userbase",userModel).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 获取全部用户信息
     */
    @ApiOperation(value = "列举所有用户信息", notes = "未测试")
    @GetMapping("/User/listAll")
    public void listAll(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.ListAll_CacheName;
        String json = cache.get(cacheName); //从缓存中读json
        if(json == null){   //如果读不到
            System.out.println("查询数据库");
            List<UserModel> userModels = userService.selectAll();
            json = Result.build(ResultType.Success).appendData("user",userModels).convertIntoJSON();
            cache.set(cacheName,json);  //把json加到缓存中
        }
        response.getWriter().write(json);   //输出到页面
    }
}
