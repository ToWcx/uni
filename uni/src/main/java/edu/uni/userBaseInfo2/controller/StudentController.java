package edu.uni.userBaseInfo2.controller;

import edu.uni.bean.Result;
import edu.uni.bean.ResultType;
import edu.uni.userBaseInfo2.bean.ApprovalMain;
import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.bean.UserinfoApplyApproval;
import edu.uni.userBaseInfo2.controller.approvalUtil.StudentAU;
import edu.uni.userBaseInfo2.controller.viewObject.*;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.ClassMateModel;
import edu.uni.userBaseInfo2.service.model.StudentModel;
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

@Api(description = "学生模块")
@Controller
@RequestMapping("/json/userBaseInfo2")    ///json/【模块名】/【操作对象】/ 选项 (如果有)
public class StudentController {
    @Autowired
    private StudentService studentService;
    private RedisCache cache;
    @Autowired
    private AddressService addressService;
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
    private SecondLevelDisciplineService secondLevelDisciplineService;
    @Autowired
    private PoliticalAffiliationService politicalAffiliationService;

    /**
     * 内部类，专门用来管理每个get方法所对应缓存的名称。
     */
    static class CacheNameHelper{
        // e_c_categories_listAll 模块名简称_操作对象_操作方法
        public static final String ListAll_CacheName = "ub2_s_students_listAll";
        // e_c_category_{类别id}
        public static final String Receive_CacheNamePrefix = "ub2_s_student_";



    }

    /**
     * 新增学生
     *
     */
    @ApiOperation(value="新增类别", notes="未测试")
    @ApiImplicitParam(name = "student", value = "类别详情实体", required = true, dataType = "Student")
    @PostMapping("/student")
    @ResponseBody
    public Result create(@RequestBody(required = false) Student student){
        if(student != null){
            boolean success = studentService.insert(student);
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
//    @ApiImplicitParam(name = "id", value = "类别id", required = true, dataType = "Long", paramType = "path")
//    @DeleteMapping("/student/{id}")
//    @ResponseBody
//    public Result destroy(@PathVariable Long id){
//        boolean success = studentService.delete(id);
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
     * @param studentAU
     * @return
     */
    @Transactional
    @ApiOperation(value="辅导员申请修改学生信息", notes="未测试")
    @ApiImplicitParam(name = "studentAU", value = "类别实体", required = true, dataType = "StudentAU")
    @PutMapping("/student")
    @ResponseBody
    public Result updateStudent(@RequestBody(required = false) StudentAU studentAU){
        if(studentAU != null) {
            StudentModel studentModel = studentAU.getStudentModel();
            System.out.println("进入student Update Line124*****************");
            Student student = convertBeanFromModel(studentModel);
            UserinfoApply userinfoApply = studentAU.getUserinfoApply();
            Date date = new Date();
            long userId = userinfoApply.getByWho();
            //根据userId查到用户的学校id
            Long uniId = userService.selectUniIdById(userinfoApply.getByWho()).getUniversityId();
            student.setDatetime(date);
            student.setDeleted(true);  //改
            student.setByWho(userinfoApply.getByWho());
            student.setUniversityId(uniId);

            boolean isSuccess = studentService.insert(student);
            if (isSuccess == true) {    //插入成功
                System.out.println("新增学生基础信息成功");
                long aId = student.getId(); //新纪录id
                userinfoApply.setNewInfoId(aId);
                userinfoApply.setStartTime(date);
                userinfoApply.setDatetime(date);

                //根据学校id和业务类型name="地址业务"找到唯一的步数规定表,获取该业务步数stepCnt以及该表id
                ApprovalMain approvalMain = approvalMainService.selectByUniIdAndName(uniId,"辅导员申请修改学生信息");
                long AMId = approvalMain.getId();
                if(approvalMain == null){
                    System.out.println("approvalMain为空 查询不到该审批步数规定表");
                    return Result.build(ResultType.Failed);
                }
                userinfoApply.setApprovalMainId(AMId);
                userinfoApply.setInfoType(6);
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
//                        cache.delete(CacheNameHelper.Receive_CacheNamePrefix + student.getId());
//                        cache.delete(CacheNameHelper.ListAll_CacheName);
                    System.out.println("插入审批流程表成功");
                    return Result.build(ResultType.Success);
                } else {
                    return Result.build(ResultType.Failed);
                }

            }else {
                System.out.println("修改学生信息失败");
                return Result.build(ResultType.Failed);
            }
        }
        return Result.build(ResultType.ParamError);
    }

    /**
     * 根据id获取学生基础信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取学生基础信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/student/{id}")
    public void receiveBase(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
            StudentModel studentModel = studentService.select(id);
            System.out.println(studentModel);
//            StudentVO studentVO = convertStudentFromModel(studentModel);
//            studentVO.setUserId(id);
//            System.out.println(studentVO);
            String json = Result.build(ResultType.Success).appendData("studentBase", studentModel ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 根据id获取学生所有信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取学生所有信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/student/ListAll/{id}")
    public void listAllById(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        StudentInfoVO studentInfoVO = new StudentInfoVO();

        studentInfoVO.setAddressModels(addressService.selectAll(id));
        studentInfoVO.setEcommModels(ecommService.selectAll(id));
        studentInfoVO.setStudentModel(studentService.select(id));
        studentInfoVO.setUserModel(null);    //缺少user接口
        System.out.println(studentInfoVO);
        String json = Result.build(ResultType.Success).appendData("studentInfo", studentInfoVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 获取全部学生基础信息
     */
    @ApiOperation(value = "列举所有类别", notes = "未测试")
    @GetMapping("/students/listAll")
    public void listAll(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.ListAll_CacheName;
        String json = cache.get(cacheName); //从缓存中读json
        if(json == null){   //如果读不到
            System.out.println("查询数据库");
            List<Student> students = studentService.selectAll();
            json = Result.build(ResultType.Success).appendData("students",students).convertIntoJSON();
            cache.set(cacheName,json);  //把json加到缓存中
        }
        response.getWriter().write(json);   //输出到页面
    }

    /**
     * 根据id获取学生历史信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取学生历史信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/students/listByUserIdAndDeleted/{id}")
    public void listByUserIdAndDeleted(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<StudentModel> studentModels = studentService.selectStudentHistory(id);
        System.out.println(studentModels);
        StudentVO studentVO = convertHistoryFromModel(studentModels);
        System.out.println(studentVO);
        studentVO.setUserId(id);
        String json = Result.build(ResultType.Success).appendData("studentBase", studentVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 根据id获取同班同学部分信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据类别id获取同班同学部分信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/students/listByUserIdAnClass/{id}")
    public void listByUserIdAnClass(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<ClassMateModel> classMateModels = studentService.selectClassMate(id);
        System.out.println(classMateModels);
        ClassMateVO classMateVO = convertClassMateFromModel(classMateModels);
        System.out.println(classMateVO);
        classMateVO.setUserId(id);
        String json = Result.build(ResultType.Success).appendData("classMate", classMateVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

    /**
     * 根据class_id获取同班同学部分信息
     * @param id
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="根据class_id获取班级同学部分信息", notes="未测试")
    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
    @GetMapping("/students/listByClassId/{id}")
    public void listByClassId(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        测试的时候需注释掉cache缓存
//        String json = cache.get(cacheName);
//        if(json == null){
        List<ClassMateModel> classMateModels = studentService.selectByClassId(id);
        System.out.println(classMateModels);
        StuByClassVO stuByClassVO = convertStuByClassFromModel(classMateModels);
        System.out.println(stuByClassVO);
        stuByClassVO.setClassId(id);
        String json = Result.build(ResultType.Success).appendData("stuentByClassId", stuByClassVO ).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
        response.getWriter().write(json);
    }

//    /**
//     * 根据id获取学生亲属信息
//     * @param id
//     * @param response
//     * @throws IOException
//     */
//     @ApiOperation(value="根据类别id获取学生亲属类别详情", notes="未测试")
//     @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
//     @GetMapping("/studentRelation/{id}")
//     public void receiveRelation(@PathVariable Long id, HttpServletResponse response) throws IOException {
//     response.setContentType("application/json;charset=utf-8");
//     String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//     //测试的时候需注释掉cache缓存
//     //        String json = cache.get(cacheName);
//     //        if(json == null){
//     StudentModel studentModel = studentService.select(id);
//     System.out.println(studentModel);
//     StudentRelationVO studentRelationVO = convertRelationFromModel(studentModel);
//     System.out.println(studentRelationVO);
//     String json = Result.build(ResultType.Success).appendData("studentRelation", studentRelationVO ).convertIntoJSON();
//     //            cache.set(cacheName, json);
//     //        }
//     response.getWriter().write(json);
//     }


//    /**
//     * 查看个人亲属信息
//     */
//    @ApiOperation(value="根据类别id获取类别详情", notes="已测试")
//    @ApiImplicitParam(name = "id", value = "类别id", required = false, dataType = "Long", paramType = "path")
//    @GetMapping("/student/{id}")
//    public void select(@PathVariable Long id, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json;charset=utf-8");
//        String cacheName = CacheNameHelper.Receive_CacheNamePrefix + id;
//        String json = cache.get(cacheName);
//        if(json == null){
//            json = Result.build(ResultType.Success).appendData("student",  studentService.select(id)).convertIntoJSON();
//            cache.set(cacheName, json);
//        }
//        response.getWriter().write(json);
//    }
//  //改成List
//    private StudentVO convertFromModel(StudentModel studentModel){
//        if(studentModel == null){
//            return null;
//        }
//        StudentVO studentVO = new StudentVO();
//        BeanUtils.copyProperties(studentModel,studentVO);
//        return studentVO;
//    }


    private Student convertBeanFromModel(StudentModel studentModel){
        if(studentModel == null){
            return null;
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentModel,student);
        System.out.println("copyProperties执行完毕");
//        long firstId = secondLevelDisciplineService.select();
//        student.setMajorId(secondLevelDisciplineService.selectByZh(studentModel.getMajor(),firstId).getId());
        student.setSpecialtyId((long)22);
        System.out.println("specialtyId set完毕");
        student.setPoliticalId(politicalAffiliationService.selectByZh(studentModel.getPolitical()).getId());
        System.out.println("PoliticalId set完毕");
        student.setLiveRoom(null);
        System.out.println("LiveRoom set完毕");
        System.out.println("student:" + student);
        return student;
    }

    private  ClassMateVO convertClassMateFromModel(List<ClassMateModel> classMateModels){
        if(classMateModels == null){
            return null;
        }
        ClassMateVO classMateVO = new ClassMateVO();
        classMateVO.setClassMateModels(classMateModels);
        return classMateVO;
    }

    private  StuByClassVO convertStuByClassFromModel(List<ClassMateModel> classMateModels) {
        if (classMateModels == null) {
            return null;
        }
        StuByClassVO stuByClassVO = new StuByClassVO();
        stuByClassVO.setClassMateModels(classMateModels);
        return stuByClassVO;
    }

        private  StudentVO convertHistoryFromModel(List<StudentModel> studentModels){
        if(studentModels == null){
            return null;
        }
        StudentVO studentVO = new StudentVO();
        studentVO.setStudentModels(studentModels);
        return studentVO;
    }

//    private StudentVO convertStudentFromModel(StudentModel studentModel){
//        if(studentModel == null){
//            return null;
//        }
//        StudentVO studentVO = new StudentVO();
//        List<StudentModel> studentModels = new ArrayList<>();
//        studentModels.add(studentModel);
//        studentVO.setStudentModels(studentModels);
//        return studentVO;
//    }

//    private StudentRelationVO convertRelationFromModel(StudentModel studentModel){
//        if(studentModel == null){
//            return null;
//        }
//        StudentRelationVO studentRelationVO = new StudentRelationVO();
//        BeanUtils.copyProperties(studentModel,studentRelationVO);
//
//        return studentRelationVO;
//    }
}
