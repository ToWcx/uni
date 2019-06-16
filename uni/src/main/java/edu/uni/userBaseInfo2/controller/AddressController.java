package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.*;
import edu.uni.userBaseInfo2.controller.approvalUtil.AddressAU;
import edu.uni.userBaseInfo2.controller.viewObject.*;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.AddrModel;
import edu.uni.userBaseInfo2.service.model.AddressModel;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(description = "用户地址模块")
@Controller
@RequestMapping("/json/userBaseInfo2")    ///json/【模块名】/【操作对象】/ 选项 (如果有)
public class AddressController {

    @Autowired
    private AddressService addressService;
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
    private AddrCountryService addrCountryService;
    @Autowired
    private AddrStateService addrStateService;
    @Autowired
    private AddrCityService addrCityService;
    @Autowired
    private AddrAreaService addrAreaService;
    @Autowired
    private AddrStreetService addrStreetService;
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

//    /**
//     * 不需要用到新增
//     * 新增类别
//     * @param address
//     * @return
//     */
//    @ApiOperation(value="新增类别", notes="已测试")
//    @ApiImplicitParam(name = "address", value = "类别详情实体", required = true, dataType = "Address")
//    @PostMapping("/address")
//    @ResponseBody
//    public Result create(@RequestBody(required = false) Address address){
//        if(address != null){
//            boolean success = addressService.insert(address);
//
//            if(success){
//                // 清空相关缓存
//                cache.delete(CacheNameHelper.ListAll_CacheName);
//                return Result.build(ResultType.Success);
//            }else{
//                return Result.build(ResultType.Failed);
//            }
//        }
//        return Result.build(ResultType.ParamError);
//    }


//    /**
//     * 删除类别
//     * @param id
//     * @return
//     */
//    @ApiOperation(value="删除类别", notes="已测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "Integer", paramType = "path")
//    @DeleteMapping("/address/{id}")
//    @ResponseBody
//    public Result destroy(@PathVariable Integer id){
//        //逻辑删除，置deleted为1
//        //待修改
//        boolean success = addressService.delete(id);
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
     * @param addressAU
     * @return
     */
    @Transactional
    @ApiOperation(value="用户申请修改地址", notes="已测试")
    @ApiImplicitParam(name = "addressAU", value = "类别实体", required = true, dataType = "AddressAU")
    @PutMapping("/Address")
    @ResponseBody
    public Result updateStudent(@RequestBody(required = false) AddressAU addressAU){
        if(addressAU != null) {
            System.out.println(addressAU);
            //********************************************
            addressAU.setCountry(addrCountryService.selectIdByCode(addressAU.getCountry()).getId());
            addressAU.setState(addrStateService.selectIdByCode(addressAU.getState()).getId());
            addressAU.setCity(addrCityService.selectIdByCode(addressAU.getCity()).getId());
            addressAU.setArea(addrAreaService.selectIdByCode(addressAU.getArea()).getId());
            addressAU.setStreet(addrStreetService.selectIdByCode(addressAU.getStreet()).getId());
            System.out.println(addressAU);
            //********************************************
            Address address = new Address();
            BeanUtils.copyProperties(addressAU,address);
            System.out.println(address);
            System.out.println("进入updateStudent Line129**************");
            UserinfoApply userinfoApply = addressAU.getUserinfoApply();
            Date date = new Date();
            long userId = userinfoApply.getByWho();
            address.setUserId(userId);
            address.setDatetime(date);
            address.setDeleted(true);  //改
            address.setByWho(userinfoApply.getByWho());

            boolean isSuccess = addressService.insert(address);
            if (isSuccess == true) {    //插入成功
                System.out.println("插入新地址成功");
                long aId = address.getId(); //新纪录id
                userinfoApply.setNewInfoId(aId);
                userinfoApply.setStartTime(date);
                userinfoApply.setDatetime(date);

                //根据userId查到用户的学校id
                Long uniId = userService.selectUniIdById(userinfoApply.getByWho()).getUniversityId();
                //根据学校id和业务类型name="地址业务"找到唯一的步数规定表,获取该业务步数stepCnt以及该表id
                ApprovalMain approvalMain = null;
                if(addressAU.getType() == 1){   //用户类型： 0:游客 1:学生  2:教职员工 3:校外职员  4:学生亲属  5:系统运营者  6:学校信息主管
                    approvalMain = approvalMainService.selectByUniIdAndName(uniId,"学生申请修改地址");
                }else if(addressAU.getType() == 2){
                    approvalMain = approvalMainService.selectByUniIdAndName(uniId,"职员申请修改地址");
                }else if(addressAU.getType() == 4){
                    approvalMain = approvalMainService.selectByUniIdAndName(uniId,"学生亲属申请修改地址");
                }
                long AMId = approvalMain.getId();
                if(approvalMain == null){
                    System.out.println("approvalMain为空 查询不到该审批步数规定表");
                    return Result.build(ResultType.Failed);
                }
                userinfoApply.setApprovalMainId(AMId);
                userinfoApply.setInfoType(1);   //审批信息种类
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
//                        cache.delete(CacheNameHelper.Receive_CacheNamePrefix + address.getId());
//                        cache.delete(CacheNameHelper.ListAll_CacheName);
                        System.out.println("插入审批流程表成功");
                        return Result.build(ResultType.Success);
                    } else {
                        return Result.build(ResultType.Failed);
                    }

            }else {
                System.out.println("修改地址信息失败 无法插入地址");
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 获取国家选项记录
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="获取国家选项记录", notes="已测试")
    @GetMapping("/address/countryZh")
    public void receiveCountryZh(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + "CountryZh";
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<AddrCountry> addrCountrys = addrCountryService.selectAll();

        String json = Result.build(ResultType.Success).appendData("AddrCountry", addrCountrys ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 获取省份选项记录
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="获取省份选项记录", notes="已测试")
    @GetMapping("/address/stateZh/{id}")
    public void receiveStateZh(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<AddrModel> addrModels = new ArrayList<>();
        List<AddrState> addrStates = addrStateService.selectByCountryCode(id);
        for(int i=0;i<addrStates.size();i++){
            AddrModel addrModel = new AddrModel();
            addrModel.setId(addrStates.get(i).getId());
            addrModel.setAddrZh(addrStates.get(i).getStateZh());
            addrModel.setCode(addrStates.get(i).getCode());
            addrModels.add(addrModel);
        }
        AddrStateVO addrVO = new AddrStateVO();
        addrVO.setCountryCode(id);
        addrVO.setAddrModels(addrModels);

        String json = Result.build(ResultType.Success).appendData("AddrState", addrVO).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 获取城市选项记录
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="获取城市选项记录", notes="已测试")
    @GetMapping("/address/cityZh/{id}")
    public void receiveCityZh(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<AddrModel> addrModels = new ArrayList<>();
        List<AddrCity> addrCities = addrCityService.selectByStateCode(id);
        for(int i=0;i<addrCities.size();i++){
            AddrModel addrModel = new AddrModel();
            addrModel.setAddrZh(addrCities.get(i).getCityZh());
            addrModel.setCode(addrCities.get(i).getCode());
            addrModels.add(addrModel);
        }
        AddrCityVO addrVO = new AddrCityVO();
        addrVO.setAddrModels(addrModels);
        addrVO.setStateCode(id);

        String json = Result.build(ResultType.Success).appendData("AddrCity", addrVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 获取地区选项记录
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="获取地区选项记录", notes="已测试")
    @GetMapping("/address/areaZh/{id}")
    public void receiveAreaZh(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<AddrModel> addrModels = new ArrayList<>();
        List<AddrArea> addrAreas = addrAreaService.selectByCityCode(id);
        for(int i=0;i<addrAreas.size();i++){
            AddrModel addrModel = new AddrModel();
            addrModel.setAddrZh(addrAreas.get(i).getAreaZh());
            addrModel.setCode(addrAreas.get(i).getCode());
            addrModels.add(addrModel);
        }
        AddrAreaVO addrVO = new AddrAreaVO();
        addrVO.setAddrModels(addrModels);
        addrVO.setCityCode(id);

        String json = Result.build(ResultType.Success).appendData("AddrArea", addrVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 获取街道选项记录
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="获取街道选项记录", notes="已测试")
    @GetMapping("/address/streetZh/{id}")
    public void receiveStreetZh(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<AddrModel> addrModels = new ArrayList<>();
        List<AddrStreet> addrStreets = addrStreetService.selectByAreaCode(id);
        for(int i=0;i<addrStreets.size();i++){
            AddrModel addrModel = new AddrModel();
            addrModel.setAddrZh(addrStreets.get(i).getStreetZh());
            addrModel.setCode(addrStreets.get(i).getCode());
            addrModels.add(addrModel);
        }
        AddrStreetVO addrVO = new AddrStreetVO();
        addrVO.setAddrModels(addrModels);
        addrVO.setAreaCode(id);

        String json = Result.build(ResultType.Success).appendData("AddrStreet", addrVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 根据id获取用户地址信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据用户id获取用户地址类别详情", notes="已测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/address/{id}")
    public void receiveAddress(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = StudentController.CacheNameHelper.Receive_CacheNamePrefix + id;
        //测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<AddressModel> addressModel = addressService.selectAll(id);
        AddressVO addressVO = convertAddressFromModel(addressModel);
        addressVO.setUserId(id);
        System.out.println(addressVO);
        String json = Result.build(ResultType.Success).appendData("address", addressVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    private Address convertBeanFromModel(AddressModel addressModel){
        if(addressModel == null){
            return null;
        }
        Address address = new Address();
        BeanUtils.copyProperties(addressModel,address);
        System.out.println("copyeProperties执行完毕");
        System.out.println(addressModel);
        address.setCountry(addrCountryService.selectByZh(addressModel.getCountry()).getId());
        System.out.println(address);
        address.setState(addrStateService.selectByZh(addressModel.getState()).getId());
        System.out.println(address);
        address.setCity(addrCityService.selectByZh(addressModel.getCity()).getId());
        System.out.println(address);
        address.setArea(addrAreaService.selectByZh(addressModel.getArea()).getId());
        System.out.println(address);
        address.setStreet(addrStreetService.selectByZh(addressModel.getStreet()).getId());
        System.out.println(address);
        System.out.println("Address的id转换字符串后：" + address);
        return address;
    }


    private AddressVO convertAddressFromModel(List<AddressModel> addressModels){
        if(addressModels == null){
            return null;
        }
        AddressVO addressVO = new AddressVO();
        addressVO.setAddress(addressModels);
        return addressVO;
    }
}
