package edu.uni.userBaseInfo2.controller;

import edu.uni.administrativestructure.bean.Department;
import edu.uni.administrativestructure.service.DepartmentService;
import edu.uni.auth.bean.User;
import edu.uni.auth.service.AuthService;
import edu.uni.auth.service.RoleService;
import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.bean.Employee;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.controller.approvalUtil.EmployeeAU;
import edu.uni.userBaseInfo2.controller.viewObject.*;
import edu.uni.userBaseInfo2.mapper.EmployeeMapper;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.*;
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

@Api(description = "教师模块")
@Controller
@RequestMapping("/json/userBaseInfo2")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private EcommService ecommService;
    @Autowired
    private UserService userService;
    @Autowired
    private ApprovalMainService approvalMainService;
    @Autowired
    private PoliticalAffiliationService politicalAffiliationService;
    @Autowired
    private UserinfoApplyService userinfoApplyService;
    @Autowired
    private ApprovalStepInchargeService approvalStepInchargeService;
    @Autowired
    private UserinfoApplyApprovalService userinfoApplyApprovalService;
    @Autowired
    private LearningDegreeService learningDegreeService;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DepartmentService departmentService;
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
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_employee_";
        // e_c_categories_listAll
        public static final String ListAll_CacheName = "ub2_employee_listAll";
        public static final String Receive_EmployeeEcomm = "ub2_employeeEcomm_";
    }

    /**
     * 新增教师
     * @param employee
     * @return
     */
    @ApiOperation(value = "新增类别",notes = "已测试")
    @ApiImplicitParam(name = "employee",value = "类别详情实体",required = true,dataType = "Employee")
    @PostMapping("/employee")
    @ResponseBody
    public Result addEmployee(@RequestBody(required = false) Employee employee){
        if(employee!=null){
            boolean success = employeeService.insert(employee);
            if(success){
                //清空相关缓存
                cache.delete(CacheNameHelper.ListAll_CacheName);
                return Result.build(ResultType.Success);
            }else {
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 人事处申请修改职员信息
     * @param employeeAU
     * @return
     */
    @Transactional
    @ApiOperation(value="人事处职员申请修改职员信息", notes="未测试")
    @ApiImplicitParam(name = "employeeAU", value = "类别实体", required = true, dataType = "EmployeeAU")
    @PutMapping("/employee")
    @ResponseBody
    public Result updateStudent(@RequestBody(required = false) EmployeeAU employeeAU){
        User user = authService.getUser();
        if(user == null){
            return Result.build(ResultType.Failed, "你沒有登錄");
        }
        long userId = user.getId();
        // 审核的信息种类 0:联系方式  1:地址 2：照片  3：亲属  4：学历  5：简历
        // 6：学生信息 7：教职工信息 8：用户个人信息 9：学生excel表  10：职员excel表
        if(userinfoApplyService.selectByUserIdAndType(userId,7) != null){
            System.out.println("已有审批记录，请等审批结束后再提交修改");
            return Result.build(ResultType.Failed,"已有审批记录，请等审批结束后再提交修改");
        }

        if(employeeAU != null) {
            EmployeeModel employeeModel = employeeAU.getEmployeeModel();
            Employee employee = convertBeanFromModel(employeeModel);
            UserinfoApply userinfoApply = employeeAU.getUserinfoApply();
            userinfoApply.setByWho(userId);
            //根据userId查到用户的学校id
            Long uniId = userService.selectUniIdById(userId).getUniversityId();
            //不修改当前住址和通讯方式以及简历
            Employee employee1 = employeeService.selectById(userinfoApply.getOldInfoId());
            employee.setUserId(userId);
            employee.setEmployHistoryId(employee1.getEmployHistoryId());
            employee.setHomeAddressId(employee1.getHomeAddressId());
            employee.setPhoneEcommId(employee1.getPhoneEcommId());
            //
            Date date = new Date();
            employee.setDatetime(date);
            employee.setDeleted(true);
            employee.setByWho(userinfoApply.getByWho());
            employee.setUniversityId(uniId);

            boolean isSuccess = employeeService.insert(employee);
            if (isSuccess == true) {    //插入成功
                System.out.println("新增职员基础信息成功");
                long aId = employee.getId(); //新纪录id
                userinfoApply.setNewInfoId(aId);
                userinfoApply.setStartTime(date);
                userinfoApply.setDatetime(date);

                //根据学校id和业务类型name="地址业务"找到唯一的步数规定表,获取该业务步数stepCnt以及该表id
                ApprovalMain approvalMain = approvalMainService.selectByUniIdAndName(uniId,"人事处申请修改职员信息");
                long AMId = approvalMain.getId();
                if(approvalMain == null){
                    System.out.println("approvalMain为空 查询不到该审批步数规定表");
                    return Result.build(ResultType.Failed);
                }
                userinfoApply.setApprovalMainId(AMId);
                userinfoApply.setInfoType(7);
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
//                        cache.delete(CacheNameHelper.Receive_CacheNamePrefix + student.getId());
//                        cache.delete(CacheNameHelper.ListAll_CacheName);
                    System.out.println("插入审批流程表成功");
                    return Result.build(ResultType.Success);
                } else {
                    return Result.build(ResultType.Failed);
                }

            }else {
                System.out.println("修改职员信息失败");
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 删除教师类别
     * @param id
     * @return
     */
    @ApiOperation(value = "删除类别",notes = "已测试")
    @ApiImplicitParam(name = "id",value = "类别id",required = true,dataType = "Long",paramType = "path")
    @DeleteMapping("/employee/{id}")
    @ResponseBody
    public Result destoryEmployee(@PathVariable Long id){
        boolean success = employeeService.delete(id);
        if(success){
            //清空相关缓存
            cache.delete(CacheNameHelper.ListAll_CacheName);
            return Result.build(ResultType.Success);
        }else {
            return Result.build(ResultType.Failed);
        }
    }

    /**
     * 获取教职工基础信息
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="获取教职工基础信息类别详情", notes="已测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employeeBase")
    public void receiveBase(HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix+id;
        //测试的时候需要注释掉cache缓存
        //String json = cache.get(cacheName);
        //if(json==null){
        EmployeeModel employeeModel = employeeService.select(id);
        EmployeeVO employeeVO = convertBaseFromModel(employeeModel);
        String json = Result.build(ResultType.Success).appendData("employeeBase", employeeVO).convertIntoJSON();
        //}
        response.getWriter().write(json);
    }

    /**
     * 根据userId获取教职工基础信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据userId获取教职工基础信息类别详情", notes="已测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employeeBase/{id}")
    public void receiveBaseById(@PathVariable Long id,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix+id;
        //测试的时候需要注释掉cache缓存
        //String json = cache.get(cacheName);
        //if(json==null){
        EmployeeModel employeeModel = employeeService.select(id);
        EmployeeVO employeeVO = convertBaseFromModel(employeeModel);
        String json = Result.build(ResultType.Success).appendData("employeeBase", employeeVO).convertIntoJSON();
        //}
        response.getWriter().write(json);
    }

    /**
     * 查看所授课班级学生信息(部分信息)
     * 根据班级id获取授课学生的部分信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据班级id获取授课学生的部分信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "classId", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employee/listByClassToStudent/{id}")
    public void ListByClassTodelete0Student(@PathVariable Long id,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<ClassToStudentListModel> classToStudentListModels = employeeService.selectdelete0ClassStudent(id);
        ClassStudentVO classStudentVO = convertClasstoStudentFromModel(classToStudentListModels);
        if(classStudentVO != null){
            classStudentVO.setUserId(id);
        }
        String json = Result.build(ResultType.Success).appendData("classStudent",classStudentVO).convertIntoJSON();
        //  cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 查看所授课班级信息
     * 根据id获取授课班级信息
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="查看所授课班级信息", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employee/listByTeachClass")
    public void ListByTeachClass(HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<TeachClassModel> teachClassModels = employeeService.selectTeachClass(id);
        System.out.println("test");
        TeachClassVO teachClassVO =convertTeachClassFromModel(teachClassModels);
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        Department department = departmentService.select(departmentId);
        teachClassVO.setDepartment(department.getName());
        String json = Result.build(ResultType.Success).appendData("teachClass",teachClassVO).convertIntoJSON();
        //  cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 查看所授课班级信息
     * 根据id获取授课班级信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="查看所授课班级信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employee/listByTeachClass/{id}")
    public void ListByTeachClassAndId(@PathVariable Long id,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<TeachClassModel> teachClassModels = employeeService.selectTeachClass(id);
        System.out.println("test");
        TeachClassVO teachClassVO =convertTeachClassFromModel(teachClassModels);
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        Department department = departmentService.select(departmentId);
        teachClassVO.setDepartment(department.getName());
        String json = Result.build(ResultType.Success).appendData("teachClass",teachClassVO).convertIntoJSON();
        //  cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 查看所处学院的所有班级信息
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="查看所处学院的所有班级信息", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employee/listByselectDepartment")
    public void ListByselectDepartment(HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<TeachClassModel> teachClassModels = employeeService.selectDepartment(id);
        System.out.println("test");
        TeachClassVO teachClassVO =convertTeachClassFromModel(teachClassModels);
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        Department department = departmentService.select(departmentId);
        teachClassVO.setDepartment(department.getName());
        String json = Result.build(ResultType.Success).appendData("teachClass",teachClassVO).convertIntoJSON();
        //  cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }
    /**
     * 班主任查看所带班级信息（根据userid）
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="查看所带班级信息", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employee/listByClassStuForHeadteacher")
    public void ListByClassStuForHeadteacher(HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<TeachClassModel> teachClassModels = employeeService.selectClassStuForHeadteacher(id);
        System.out.println("test");
        TeachClassVO teachClassVO =convertTeachClassFromModel(teachClassModels);
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        Department department = departmentService.select(departmentId);
        teachClassVO.setDepartment(department.getName());
        String json = Result.build(ResultType.Success).appendData("teachClass",teachClassVO).convertIntoJSON();
        //  cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 查看班主任所带班级信息（根据userid）
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="查看所带班级信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employee/listByClassStuForHeadteacher/{id}")
    public void ListByClassStuForHeadteacherAndId(@PathVariable Long id,HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<TeachClassModel> teachClassModels = employeeService.selectClassStuForHeadteacher(id);
        System.out.println("test");
        TeachClassVO teachClassVO =convertTeachClassFromModel(teachClassModels);
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        Department department = departmentService.select(departmentId);
        teachClassVO.setDepartment(department.getName());
        String json = Result.build(ResultType.Success).appendData("teachClass",teachClassVO).convertIntoJSON();
        //  cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 查看本学院学生信息
     * 根据id获取学院学生的全部信息(包括历史信息)
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取本学院学生全部信息", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/employee/listByDepartmentdelete1ToStudent")
    public void ListByDepartmentdelete1ToStudent(HttpServletResponse response) throws  IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<DepartmentToStudentListModel> departmentToStudentListModels = employeeService.selectdelete1DepartStudent(id);
        DepartmentStudentVO departmentStudentVO =convertDepartmenttoStudentFromModel(departmentToStudentListModels);
        departmentStudentVO.setUserId(id);
        String json = Result.build(ResultType.Success).appendData("departmentStudentVO",departmentStudentVO).convertIntoJSON();
        //  cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

//    /**
//     * 查看本学院学生信息
//     * 根据id获取学院学生的全部信息(有效信息)
//     * @param id
//     * @param response
//     * @throws IOException
//     */
//    @ApiOperation(value="根据类别id获取本学院学生全部信息(有效信息)", notes="未测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
//    @GetMapping("/employee/listByDepartmentdelete0ToStudent/{id}")
//    public void ListByDepartmentdelete0ToStudent( @PathVariable Long id,HttpServletResponse response) throws  IOException{
//        response.setContentType("application/json;charset=utf-8");
//        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
////        测试的时候需注释掉cache缓存
////        String json = cache.get(cacheName);
////        if(json == null){
//        List<DepartmentToStudentListModel> departmentToStudentListModels = employeeService.selectdelete0DepartStudent(id);
//        DepartmentStudentVO departmentStudentVO =convertDepartmenttoStudentFromModel(departmentToStudentListModels);
//        departmentStudentVO.setUserId(id);
//        String json = Result.build(ResultType.Success).appendData("departmentStudentVO",departmentStudentVO).convertIntoJSON();
//        //  cache.set(cacheName, json);
////        }
//        response.getWriter().write(json);
//    }

    /**
     * 根据id查询教职工全部信息(
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "根据id查询教职工全部信息类别详情",notes = "未测试")
//    @ApiImplicitParam(name = "id",value = "类别id",required = false,dataType = "Long",paramType = "path")
    @GetMapping("/employee/ListAll")
    public void receiveEmployee(HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix+id;
        //String json = cache.get(cacheName);
        //if(json==null){
        EmployeeInfoVO employeeInfoVO = new EmployeeInfoVO();
        employeeInfoVO.setAddressModels(addressService.selectAll(id));
        employeeInfoVO.setEcommModels(ecommService.selectAll(id));
        employeeInfoVO.setEmployeeModel(employeeService.select(id));
        employeeInfoVO.setLearningDegreeModels(learningDegreeService.selectAll(id));
        employeeInfoVO.setEmployeeHistoryModels(employeeHistoryService.selectAll(id));
        employeeInfoVO.setUserModel(userService.select(id));
        System.out.println(employeeInfoVO);
        String json= Result.build(ResultType.Success).appendData("employeeInfo",employeeInfoVO).convertIntoJSON();
        //cache.set(cacheName,json);
        // }
        response.getWriter().write(json);
    }

    /**
     * 根据id查询教职工全部信息(
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "根据id查询教职工全部信息类别详情",notes = "未测试")
    @ApiImplicitParam(name = "id",value = "类别id",required = false,dataType = "Long",paramType = "path")
    @GetMapping("/employee/ListAll/{id}")
    public void receiveEmployeeById(@PathVariable Long id, HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix+id;
        //String json = cache.get(cacheName);
        //if(json==null){
        EmployeeInfoVO employeeInfoVO = new EmployeeInfoVO();
        employeeInfoVO.setAddressModels(addressService.selectAll(id));
        employeeInfoVO.setEcommModels(ecommService.selectAll(id));
        employeeInfoVO.setEmployeeModel(employeeService.select(id));
        employeeInfoVO.setLearningDegreeModels(learningDegreeService.selectAll(id));
        employeeInfoVO.setEmployeeHistoryModels(employeeHistoryService.selectAll(id));
        employeeInfoVO.setUserModel(userService.select(id));
        System.out.println(employeeInfoVO);
        String json= Result.build(ResultType.Success).appendData("employeeInfo",employeeInfoVO).convertIntoJSON();
        //cache.set(cacheName,json);
        // }
        response.getWriter().write(json);
    }

    /**
     * 列举所有类别
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "列举所有类别",notes = "已测试")
    @GetMapping(value = "/employee/listAllEmployee")
    public void list(HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.ListAll_CacheName;
        // String json = cache.get(cacheName);
        //if(json==null){
        String json = Result.build(ResultType.Success).appendData("employee",employeeService.selectAll()).convertIntoJSON();
        //  cache.set(cacheName,json);
        //    }
        response.getWriter().write(json);
    }

    /**
     * 显示同一学校所有教职工列表(
     * @param response
     * @throws IOException
     */
    @ApiOperation(value = "显示同一学校所有教职工列表",notes = "未测试")
//    @ApiImplicitParam(name = "id",value = "类别id",required = false,dataType = "Long",paramType = "path")
    @GetMapping("/employee/showEmployeeList")
    public void showEmployeeList(HttpServletResponse response)throws IOException{
        response.setContentType("application/json;charset=utf-8");
        User user = authService.getUser();
        if(user == null){
            return;
        }
        long id = user.getId();
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix+id;
        //String json = cache.get(cacheName);
        //if(json==null){
        List<EmployeeListModel> employeeListModels = employeeService.showEmployeeList(id);
        ListEmployeeVO listEmployeeVO = convertListEmployeeFromModel(employeeListModels);

        listEmployeeVO.setUserId(id);
        String json= Result.build(ResultType.Success).appendData("listEmployeeVO",listEmployeeVO).convertIntoJSON();
        //cache.set(cacheName,json);
        // }
        response.getWriter().write(json);
    }


    private EmployeeVO convertBaseFromModel(EmployeeModel employeeModel){
        if(employeeModel == null){
            return null;
        }
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employeeModel, employeeVO);
        return employeeVO;
    }

    private ClassStudentVO convertClasstoStudentFromModel(List<ClassToStudentListModel> classToStudentListModels){
        if(classToStudentListModels ==  null) {
            return null;
        }
        ClassStudentVO classStudentVO = new ClassStudentVO();
        classStudentVO.setClassToStudentListModels(classToStudentListModels);
        return classStudentVO;
    }

    private ListEmployeeVO convertListEmployeeFromModel(List<EmployeeListModel> employeeListModels){
        if(employeeListModels == null){
            return null;
        }
        ListEmployeeVO listEmployeeVO = new ListEmployeeVO();
        listEmployeeVO.setEmployeeListModels(employeeListModels);
        return listEmployeeVO;
    }

    private DepartmentStudentVO convertDepartmenttoStudentFromModel(List<DepartmentToStudentListModel> departmentToStudentListModels){
        if(departmentToStudentListModels ==  null) {
            return null;
        }
        DepartmentStudentVO departmentStudentVO = new DepartmentStudentVO();

        departmentStudentVO.setDepartmentToStudentListModels(departmentToStudentListModels);
        return departmentStudentVO;
    }

    private Employee convertBeanFromModel(EmployeeModel employeeModel){
        if(employeeModel == null){
            return null;
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeModel,employee);
//        employee.setDepartmentId(employeeModel.getDepartment());
        employee.setPoliticalId(politicalAffiliationService.selectByZh(employeeModel.getPolitical()).getId());
//        long firstId = secondLevelDisciplineService.select();
//        student.setDisciplineId(secondLevelDisciplineService.selectByZh(employeeModel.getMajor(),firstId).getId());
        employee.setDisciplineId((long)22); //需要第一学科id
        employee.setDepartmentId((long)15); //缺少接口
        employee.setSubdepartmentId((long)1);   //缺少接口
        return employee;
    }

    private TeachClassVO convertTeachClassFromModel(List<TeachClassModel> teachClassModels){
        if(teachClassModels == null){
            return null;
        }
        TeachClassVO teachClassVO = new TeachClassVO();
        teachClassVO.setTeachClassModels(teachClassModels);
        return teachClassVO;
    }
}
