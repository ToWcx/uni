package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.Picture;
import edu.uni.userBaseInfo2.service.PictureService;
import edu.uni.utils.RedisCache;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

@Api(description = "照片模块")
@Controller
@RequestMapping("/json/userBaseInfo2")
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @Autowired
    private RedisCache cache;

    //    //获取本机ip
    private String host;
    //图片存放根路径
    private String rootPath = "D:";
    //图片存放根目录下的子目录
    private String sonPath = "/img/";
    //获取图片链接
    private String imgPath;
    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // e_c_categories_listAll 模块名简称_操作对象_操作方法 （学生的不知道要不要和职员的分开）
        public static final String ListAll_CacheName = "ub2_s_picture_listAll";
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_s_picture_";
    }

    @ApiOperation(value = "图片上传", notes = "图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag", value = "照片类型,0:证件照，1:生活照", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "long", paramType = "query"),
    })
    @PostMapping(value="/picture",headers="content-type=multipart/form-data")
    @ResponseBody
    public Result upload(HttpServletRequest request,
                         HttpServletResponse response,
                         @ApiParam(name = "file",value = "照片文件", required = true) MultipartFile file,
                         int flag,
                         long userId) throws IOException {
        //返回上传的文件是否为空，即没有选择任何文件，或者所选文件没有内容。
        //防止上传空文件导致奔溃
        if (file.isEmpty()) {
            return Result.build(ResultType.Failed);
        }

//        获取本机IP
        host = InetAddress.getLocalHost().getHostAddress();

        // 获取文件名
        String fileName = host + file.getOriginalFilename();
        //logger.info("上传的文件名为：" + fileName);
        // 设置文件上传后的路径
        String filePath = rootPath + sonPath;
        logger.info("上传的文件路径" + filePath);
        logger.info("整个图片路径："+ sonPath + fileName);
        //创建文件路径
        File dest = new File(filePath + fileName);

        String imgPath = (sonPath + fileName).toString();

        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        //transferTo（dest）方法将上传文件写到服务器上指定的文件
        file.transferTo(dest);
        //将链接保存到URL中
//      imgTest imgTest = service.add(new imgTest(), imgPath);
        Picture picture = new Picture();
        picture.setPictureName(imgPath);
        picture.setByWho((long)1100);
        Date date = new Date();
        picture.setDatetime(date);
        if(flag == 0){
            picture.setFlag(false);
        } else {
            picture.setFlag(true);
        }
        picture.setUniversityId((long)1);
        picture.setUserId(userId);
        picture.setDeleted(false);
        boolean success = pictureService.insert(picture);
        if(success){
            // 清空相关缓存
//            cache.delete(CacheNameHelper.ListAll_CacheName);
            return Result.build(ResultType.Success);
        }else{
            return Result.build(ResultType.Failed);
        }
    }
//    /**
//     * 删除图片类别
//     * @param id
//     * @return
//     */
//    @ApiOperation(value = "删除类别",notes = "已测试")
//    @ApiImplicitParam(name = "id",value = "类别id",required = true,dataType = "Long",paramType = "path")
//    @DeleteMapping("/picture/{id}")
//    @ResponseBody
//    private Result destory(@PathVariable  Integer id){
//        boolean success = pictureService.delete(id);
//        if(success){
//            cache.delete(CacheNameHelper.ListAll_CacheName);
//            return Result.build(ResultType.Success);
//        }else{
//            return Result.build(ResultType.Failed);
//        }
//    }
//    /**
//     * 更新类别
//     * @param picture
//     * @return
//     */
//    @ApiOperation(value="更新类别id更新类别信息", notes="已测试")
//    @ApiImplicitParam(name = "picture", value = "类别实体", required = true, dataType = "Picture")
//    @PutMapping("/picture")
//    @ResponseBody
//    private Result update(@RequestBody(required = false) Picture picture){
//        if(picture!=null && picture.getId()!=null){
//            boolean success = pictureService.delete(picture.getId());
//            if(success){
//                cache.delete(CacheNameHelper.Receive_CacheNamePrefix + picture.getId());
//                cache.delete(CacheNameHelper.ListAll_CacheName);
//                return Result.build(ResultType.Success);
//            }else{
//                return Result.build(ResultType.Failed);
//            }
//        }
//        return Result.build(ResultType.ParamError);
//    }
//
//    /**
//     * 根据id获取图片信息
//     * @param id
//     * @param response
//     * @throws IOException
//     */
//    @ApiOperation(value="根据类别id获取图片类别详情", notes="已测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
//    @GetMapping("/studentAddress/{id}")
//    private void receive(@PathVariable Long id, HttpServletResponse response) throws  IOException{
//        response.setContentType("application/json;charset=utf-8");
//        String cacheName = CacheNameHelper.Receive_CacheNamePrefix +id;
//        //测试时需要注释掉cache缓存
//        //String json = cache.get(cacheName);
//        // if(json ==null){
//        List<PictureModel> pictureModels = pictureService.selectAll(id);
//        PictureVO pictureVO = convertFomModel(pictureModels);
//        pictureVO.setUserId(id);
//        System.out.println(pictureVO);
//        String json = Result.build(ResultType.Success).appendData("picture",pictureVO).convertIntoJSON();
//        //cache.set(cacheName,json);
//        //}
//        response.getWriter().write(json);
//    }
//
//    private PictureVO convertFomModel(List<PictureModel> pictureModels){
//        if(pictureModels ==null){
//            return null;
//        }
//        PictureVO pictureVO = new PictureVO();
//        pictureVO.setPictureModels(pictureModels);
//        return pictureVO;
//    }
}
