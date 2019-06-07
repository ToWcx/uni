package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.Picture;
import edu.uni.userBaseInfo2.controller.viewObject.PictureVO;
import edu.uni.userBaseInfo2.service.PictureService;
import edu.uni.userBaseInfo2.service.model.PictureModel;
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

@Api(description = "照片模块")
@Controller
@RequestMapping("/json/userBaseInfo2/picture")
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @Autowired
    private RedisCache cache;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // e_c_categories_listAll 模块名简称_操作对象_操作方法 （学生的不知道要不要和职员的分开）
        public static final String ListAll_CacheName = "ub2_s_picture_listAll";
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_s_picture_";
    }

    /**
     * 新增类别
     * @param picture
     * @return
     */
    @ApiOperation(value="新增类别", notes="已测试")
    @ApiImplicitParam(name = "picture", value = "类别详情实体", required = true, dataType = "Picture")
    @PostMapping("/picture")
    @ResponseBody
    private Result create(@RequestBody(required = false) Picture picture){
        if(picture!=null) {
            boolean success = pictureService.insert(picture);
            if(success){
                //清除相关缓存
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }
    /**
     * 删除图片类别
     * @param id
     * @return
     */
    @ApiOperation(value = "删除类别",notes = "已测试")
    @ApiImplicitParam(name = "id",value = "类别id",required = true,dataType = "Long",paramType = "path")
    @DeleteMapping("/picture/{id}")
    @ResponseBody
    private Result destory(@PathVariable  Integer id){
        boolean success = pictureService.delete(id);
        if(success){
            cache.delete(CacheNameHelper.ListAll_CacheName);
            return Result.build(ResultType.Success);
        }else{
            return Result.build(ResultType.Failed);
        }
    }
    /**
     * 更新类别
     * @param picture
     * @return
     */
    @ApiOperation(value="更新类别id更新类别信息", notes="已测试")
    @ApiImplicitParam(name = "picture", value = "类别实体", required = true, dataType = "Picture")
    @PutMapping("/picture")
    @ResponseBody
    private Result update(@RequestBody(required = false) Picture picture){
        if(picture!=null && picture.getId()!=null){
            boolean success = pictureService.delete(picture.getId());
            if(success){
                cache.delete(CacheNameHelper.Receive_CacheNamePrefix + picture.getId());
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            }else{
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 根据id获取图片信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取图片类别详情", notes="已测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/studentAddress/{id}")
    private void receive(@PathVariable Long id, HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix +id;
        //测试时需要注释掉cache缓存
        //String json = cache.get(cacheName);
        // if(json ==null){
        List<PictureModel> pictureModels = pictureService.selectAll(id);
        PictureVO pictureVO = convertFomModel(pictureModels);
        pictureVO.setUserId(id);
        System.out.println(pictureVO);
        String json = Result.build(ResultType.Success).appendData("picture",pictureVO).convertIntoJSON();
        //cache.set(cacheName,json);
        //}
        response.getWriter().write(json);
    }

    private PictureVO convertFomModel(List<PictureModel> pictureModels){
        if(pictureModels ==null){
            return null;
        }
        PictureVO pictureVO = new PictureVO();
        pictureVO.setPictureModels(pictureModels);
        return pictureVO;
    }
}
