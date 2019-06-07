package edu.uni.userBaseInfo2.service.impl;

import edu.uni.administrativestructure.bean.Class;
import edu.uni.administrativestructure.service.ClassService;
import edu.uni.educateAffair.VO.CurriculumVO;
import edu.uni.educateAffair.VO.CurriculumWithCondition;
import edu.uni.educateAffair.bean.Curriculum;
import edu.uni.educateAffair.mapper.CurriculumMapper;
import edu.uni.educateAffair.service.CurriculumService;
import edu.uni.userBaseInfo2.bean.Employee;
import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.bean.User;
import edu.uni.userBaseInfo2.mapper.*;
import edu.uni.userBaseInfo2.service.*;
import edu.uni.userBaseInfo2.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private AddressMapper addressMapper ;
    @Autowired
    private EcommMapper ecommMapper;
    @Autowired
    private EmployeeHistoryMapper employeeHistoryMapper;
    @Autowired
    private SecondLevelDisciplineMapper secondLevelDisciplineMapper;
    @Autowired
    private PoliticalAffiliationMapper politicalAffiliationMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LearningDegreeMapper learningDegreeMapper;
    @Autowired
    private CurriculumMapper curriculumMapper;
    @Autowired
    private CurriculumService curriculumService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassService classService;
    @Autowired
    private EcommService ecommService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private LearningDegreeService learningDegreeService;



    @Override
    public boolean insert(Employee employee) {
        return employeeMapper.insert(employee)>0 ? true:false;
    }
//
//    @Override
//    public boolean update(Employee employee) {
//        return employeeMapper.updateByUserIdSelective(employee)>0 ?true:false;
//    }

    @Override
    public boolean delete(Long id) {
        return employeeMapper.deleteByUserId(id)>0 ? true : false;
    }

    @Override
    public boolean updateByUserId(long id) {
        return employeeMapper.updateByUserId(id) > 0 ? true : false;
    }

    @Override
    public boolean updateTrueById(long id) {
        return employeeMapper.updateTrueById(id) > 0 ? true : false;
    }

    /**
     * 查询当前有效的教职工信息(不包括历史信息)
     * @param id
     * @return
     */
    @Override
    public EmployeeModel select(long id) {
        Employee employees = employeeMapper.selectByUserIdAndDelete(id);
        EmployeeModel employeeModel = convertFromBean(employees);
        //System.out.println(employeeModel);
        return employeeModel;
    }

    @Override
    public Employee selectById(long id) {
        return employeeMapper.selectById(id);
    }

    /**
     * 查询教职工信息(包括历史信息)
     * @param id
     * @return
     */
    @Override
    public EmployeeModel selectwithoutdelete(long id) {
        Employee employees = employeeMapper.selectByUserId(id);
        EmployeeModel employeeModel = convertFromBean(employees);
        return employeeModel;
    }

    /**
     * 列举所有职员表的信息(有效)
     * @return
     */
    @Override
    public List<Employee> selectAll() {

        return employeeMapper.selectAllByDeleted(null);
    }


    /**
     * 通过教师id查询班级id,再通过班级id查询(包含历史)
     * @param id
     * @return
     */
    @Override
    public List<ClassToStudentModel> selectdelete1ClassStudent(long id) {

        return null;
    }

    /**
     * 查询本学院的所有学生的所有信息
     * 根据user_id查询学院id,再通过学院id显示学生信息(包含历史)
     * @param id
     * @return
     */
    @Override
    public List<DepartmentToStudentListModel> selectdelete1DepartStudent(long id) {
        //根据教师id获取department_id
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        System.out.println("departmentId---"+departmentId);
        //根据department_id获取class_id
        List<Class> classes = classService.selectPageByDepartment(departmentId);
        System.out.println("000classes"+classes);
        List<DepartmentToStudentListModel> departmentToStudentListModels = new ArrayList<>();
        System.out.println("classsize---"+classes.size());
        for(int i = 0;i<classes.size();i++){
            DepartmentToStudentListModel departmentToStudentListModel = new DepartmentToStudentListModel();
            List<DepartmentToStudentModel> departmentToStudentModels = new ArrayList<>();
            Long classId = classes.get(i).getId();
            //System.out.println("classId"+classId);
            //根据班级id 找到授课所有学生姓名
            List<Student> stuNos = studentMapper.selectStuNosByClass(classId);
            System.out.println("stuNos---"+stuNos);
            //根据班级id找到同班所有同学userId
            List<Student> ids = studentMapper.selectIdsByClass(classId);
            System.out.println("ids---"+ids);
            //同班同学的姓名
            List<User> userNames = userMapper.selectUserNamesByIds(ids);
            System.out.println("userNames"+userNames);
            for(int j = 0; j <stuNos.size();j++){
                DepartmentToStudentModel departmentToStudentModel = new DepartmentToStudentModel();
                System.out.println(ids.get(i).getUserId());
                System.out.println("test0---"+studentService.select(ids.get(i).getUserId()));
                departmentToStudentModel.setStudentModels(studentService.select(ids.get(i).getUserId()));
                System.out.println("test1---");
                departmentToStudentModel.setEcommModels(ecommService.selectAll(ids.get(i).getUserId()));
                System.out.println("test2---");
                departmentToStudentModel.setAddressModels(addressService.selectAll(ids.get(i).getUserId()));
                departmentToStudentModel.setLearningDegreeModels(learningDegreeService.selectAll(ids.get(i).getUserId()));
                departmentToStudentModels.add(departmentToStudentModel);
            }
            departmentToStudentListModel.setDepartmentToStudentModels(departmentToStudentModels);
            departmentToStudentListModels.add(departmentToStudentListModel);
        }
        return departmentToStudentListModels;
    }


    public void selectClassStudent(Long id){

    }

    /**
     * 查看所授课班级学生信息(不包含历史)
     * @param id
     * @return
     */
    @Override
    public List<ClassToStudentListModel> selectdelete0ClassStudent(long id) {
        //根据userId查询教师id
        Long employeeId1 = employeeMapper.selectByUserIdAndDelete(id).getId();
        System.out.println("employeeId1-----"+employeeId1);
//        CurriculumWithCondition curriculumWithCondition = new CurriculumWithCondition();
//        curriculumWithCondition.setEmployeeId(id);
//        curriculumWithCondition.setClass(true);
//        System.out.println(curriculumWithCondition);
        List<ClassToStudentListModel> classToStudentListModels = new ArrayList<>();
        List<Long> semesterId =null ;
        List<Long> employeeId =new ArrayList<>();
        employeeId.add(employeeId1);
        List<Long> courseId =null ;
        List<Long> classId =null;
        //根据教师id查询班级id
        List<Curriculum> curriculumList = curriculumService.selectCurriculumByCondition(semesterId,employeeId,courseId,classId);

        System.out.println("size---"+curriculumList.size());
        for (int i = 0; i < curriculumList.size(); i++) {	//循环每个班级
			//存的是班级的学生
            ClassToStudentListModel classToStudentListModel  = new ClassToStudentListModel();	//代表一个授课班级的学生
			//↓添加修改
			List<ClassToStudentModel> classToStudentModels = new ArrayList<>();	//代表一个授课班级的学生
			//↑添加修改
            //根据班级id 找到授课所有学生姓名
            System.out.println("classid---"+curriculumList.get(i).getClassId());
            List<Student> stuNos = studentMapper.selectStuNosByClass(curriculumList.get(i).getClassId());
            System.out.println("stuNos---"+stuNos);
            //根据班级id找到同班所有同学userId
            List<Student> ids = studentMapper.selectIdsByClass(curriculumList.get(i).getClassId());
            System.out.println(ids);
            //同班同学的姓名
            List<User> userNames = userMapper.selectUserNamesByIds(ids);
            System.out.println(userNames);
            for (int j = 0; j < stuNos.size(); j++) {	//循环每个班级的每个学生
                ClassToStudentModel classToStudentModel = new ClassToStudentModel();	//代表一个学生
                classToStudentModel.setUserName(userNames.get(j).getUserName());
                classToStudentModel.setEcommModels(ecommService.selectAll(ids.get(j).getUserId()));
                // classToStudentModel.setPictureModel();
                classToStudentModel.setStuNo(stuNos.get(j).getStuNo());
                //classToStudentListModel.add(classToStudentModel);
				classToStudentModels.add(classToStudentModel);
            }
            classToStudentListModel.setClassToStudentModels(classToStudentModels);
			classToStudentListModels.add(classToStudentListModel);
        }
        return classToStudentListModels;
    }
    /**
     * 查询本学院的所有学生的所有信息
     * 根据user_id查询学院id,再通过学院id显示学生信息(有效)
     * @param id
     * @return
     */
    @Override
    public List<DepartmentToStudentListModel> selectdelete0DepartStudent(long id) {
       // Long classId = studentMapper.selectByUserId(id).getClassId();

        return null;
    }


    private EmployeeModel convertFromBean(Employee employee){
        EmployeeModel employeeModel = new EmployeeModel();
        if(employee!=null){
            //BeanUtils.copyProperties(employee,employeeModel);
            //根据discipline.id找到category_id的值
            String major = secondLevelDisciplineMapper.selectByPrimaryKey(employee.getDisciplineId()).getCategoryId();
            String political = politicalAffiliationMapper.selectByPrimaryKey(employee.getPoliticalId()).getPolitical();
            employeeModel.setId(employee.getId());
            employeeModel.setEmpNo(employee.getEmpNo());
            employeeModel.setDepartment(employee.getDepartmentId().toString());
            employeeModel.setSubdepartment(employee.getSubdepartmentId().toString());
            employeeModel.setPosition(employee.getPositionId().toString());
            employeeModel.setDiscipline(major);
            employeeModel.setPolitical(political);
        }
        return employeeModel;
    }

}
