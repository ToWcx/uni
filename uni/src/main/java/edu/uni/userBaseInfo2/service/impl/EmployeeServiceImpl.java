package edu.uni.userBaseInfo2.service.impl;

import edu.uni.administrativestructure.bean.Class;
import edu.uni.administrativestructure.bean.Classmate;
import edu.uni.administrativestructure.bean.ClassmatePosition;
import edu.uni.administrativestructure.bean.DepartmentClass;
import edu.uni.administrativestructure.mapper.ClassMapper;
import edu.uni.administrativestructure.mapper.ClassmateMapper;
import edu.uni.administrativestructure.mapper.ClassmatePositionMapper;
import edu.uni.administrativestructure.mapper.DepartmentClassMapper;
import edu.uni.administrativestructure.service.ClassService;
import edu.uni.educateAffair.VO.CurriculumVO;
import edu.uni.educateAffair.VO.CurriculumWithCondition;
import edu.uni.educateAffair.bean.Curriculum;
import edu.uni.educateAffair.mapper.CurriculumMapper;
import edu.uni.educateAffair.service.CurriculumService;
import edu.uni.userBaseInfo2.bean.*;
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
    private ClassmatePositionMapper classmatePositionMapper;
    @Autowired
    private ClassmateMapper classmateMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private DepartmentClassMapper departmentClassMapper;
    @Autowired
    private LearningDegreeService learningDegreeService;



    @Override
    public boolean insert(Employee employee) {
        return employeeMapper.insert(employee)>0 ? true:false;
    }

    @Override
    public boolean updateById(long id) {
        return employeeMapper.updateById(id) > 0 ? true : false;
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

//    @Override
//    public boolean updateByUserId(long id) {
//        return employeeMapper.updateByUserId(id) > 0 ? true : false;
//    }

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
            if(stuNos.size() == 0){
                continue;
            }
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

    /**
     * 查看所授课的班级
     * @param id
     * @return
     */
    @Override
    public List<TeachClassModel> selectTeachClass(long id) {
        //根据userid获取department_id
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        System.out.println("departmentId---"+departmentId);

        //根据department_id获取class_id
        List<Class> classes = classService.selectPageByDepartment(departmentId);
        System.out.println("000classes"+classes);

        List<TeachClassModel> teachClassModels = new ArrayList<>();
        System.out.println("classsize---"+classes.size());
        for(int i=0;i<classes.size();i++){
            TeachClassModel teachClassModel = new TeachClassModel();
            List<TeachClassModel> teachClassModels1 = new ArrayList<>();
            //根据班级id找到同班所有同学userId
            List<Student> ids = studentMapper.selectIdsByClass(classes.get(i).getId());
            Long classId = classes.get(i).getId();
            String className = classes.get(i).getName();
            int menbernunmber = ids.size();
            Long headteacherId = classes.get(i).getHeadteacher();
            //通过emplpyee_id 找到UserName
            Employee employee = employeeMapper.selectById(headteacherId);
            System.out.println("employee----"+employee.getUserId());
            User teacher =userMapper.selectUserNameByUserId(employee.getUserId());
            String headteacher = teacher.getUserName();
            System.out.println("headteacher----"+headteacher);
           // String position =
            //通过classmate_position = 25找到classmate_id
            List<ClassmatePosition> classmatePositions = classmatePositionMapper.selectClassmateIdByPositionId((long)25);
            System.out.println("test1");
            List<Classmate> classmates = new ArrayList<>();
            Student students = new Student();
            //班长的名字
            String position  = null;
            //通过classmate_id获取student_id

            teachClassModel.setClassId(classId);
            teachClassModel.setClassCode(classes.get(i).getCode());
            teachClassModel.setClassName(className);
            teachClassModel.setMenbernunmber(menbernunmber);
            teachClassModel.setHeadteacher(headteacher);
            System.out.println("test2");
            long banz = classmateMapper.selectBanz();
           // System.out.println("test2");
            System.out.println(banz);
            Student student = studentMapper.selectByPrimaryKey(banz);
            User user = userMapper.selectUserNameByUserId(student.getUserId());
            teachClassModel.setPosition(user.getUserName());
            teachClassModels.add(teachClassModel);
//            for(int j=0;j<classmatePositions.size();j++){
//                classmates = classmateMapper.selectStudentIdByClassmateId(classmatePositions.get(j).getClassmateId());
//                students = studentMapper.selectByPrimaryKey(classmates.get(j).getId());
//                position = userMapper.selectUserNameByUserId(students.getUserId());
//
//            }
        }
        return teachClassModels;
    }

    /**
     * 查看班主任所带班级的学生
     * 根据教师id查找该教师所带班级
     * @param id
     * @return
     */
    @Override
    public List<TeachClassModel> selectClassStuForHeadteacher(long id) {
        //根据userid查找到employeeid
        Long employeeId = employeeMapper.selectByUserId(id).getId();
        System.out.println("employeeId----"+employeeId);
        //查找该教师所带班级
        List<Class> classes = classMapper.selectByheadteacher(employeeId);
        if(classes!=null){
            List<TeachClassModel> teachClassModels = new ArrayList<>();
            System.out.println("classsize---"+classes.size());
            for(int i=0;i<classes.size();i++){
                TeachClassModel teachClassModel = new TeachClassModel();
                List<TeachClassModel> teachClassModels1 = new ArrayList<>();
                //根据班级id找到同班所有同学userId
                List<Student> ids = studentMapper.selectIdsByClass(classes.get(i).getId());
                Long classId = classes.get(i).getId();
                String className = classes.get(i).getName();
                int menbernunmber = ids.size();
                Long headteacherId = classes.get(i).getHeadteacher();
                //通过emplpyee_id 找到UserName
                Employee employee = employeeMapper.selectById(headteacherId);
                System.out.println("employee----"+employee.getUserId());
                User teacher =userMapper.selectUserNameByUserId(employee.getUserId());
                String headteacher = teacher.getUserName();
                System.out.println("headteacher----"+headteacher);
                // String position =
                //通过classmate_position = 25找到classmate_id
                List<ClassmatePosition> classmatePositions = classmatePositionMapper.selectClassmateIdByPositionId((long)25);
                System.out.println("test1");
                List<Classmate> classmates = new ArrayList<>();
                Student students = new Student();
                //班长的名字
                String position  = null;
                //通过classmate_id获取student_id
                teachClassModel.setClassId(classId);
                teachClassModel.setClassName(className);
                teachClassModel.setMenbernunmber(menbernunmber);
                teachClassModel.setHeadteacher(headteacher);
                System.out.println("test2");
                long banz = classmateMapper.selectBanz();
                // System.out.println("test2");
                System.out.println(banz);
                Student student = studentMapper.selectByPrimaryKey(banz);
                User user = userMapper.selectUserNameByUserId(student.getUserId());
                teachClassModel.setPosition(user.getUserName());
                teachClassModels.add(teachClassModel);
//            for(int j=0;j<classmatePositions.size();j++){
//                classmates = classmateMapper.selectStudentIdByClassmateId(classmatePositions.get(j).getClassmateId());
//                students = studentMapper.selectByPrimaryKey(classmates.get(j).getId());
//                position = userMapper.selectUserNameByUserId(students.getUserId());
//
//            }
            }
            return teachClassModels;
        }
        return null;
    }
    /**
     * 按照userid查看所处学院的所有班级信息
     * @param id
     * @return
     */
    @Override
    public List<TeachClassModel> selectDepartment(long id) {
        //根据userid获取department_id
        Long departmentId = employeeMapper.selectByUserId(id).getDepartmentId();
        System.out.println("departmentId---"+departmentId);

        //根据department_id获取class_id
        List<DepartmentClass> departmentClasses = departmentClassMapper.selectByDepartmentId(departmentId);
        System.out.println("000classes"+departmentClasses);
        List<TeachClassModel> teachClassModels = new ArrayList<>();
        System.out.println("classsize---"+departmentClasses.size());
        for(int i=0;i<departmentClasses.size();i++){
            Class classes = classMapper.selectByPrimaryKey(departmentClasses.get(i).getClassId());
            TeachClassModel teachClassModel = new TeachClassModel();
            List<TeachClassModel> teachClassModels1 = new ArrayList<>();
            //根据班级id找到同班所有同学userId
            List<Student> ids = studentMapper.selectIdsByClass(classes.getId());
            Long classId = classes.getId();
            String className = classes.getName();
            int menbernunmber = ids.size();
            Long headteacherId = classes.getHeadteacher();
            //通过emplpyee_id 找到UserName
            Employee employee = employeeMapper.selectById(headteacherId);
            System.out.println("employee----"+employee.getUserId());
            User teacher =userMapper.selectUserNameByUserId(employee.getUserId());
            String headteacher = teacher.getUserName();
            System.out.println("headteacher----"+headteacher);
            // String position =
            //通过classmate_position = 25找到classmate_id
            List<ClassmatePosition> classmatePositions = classmatePositionMapper.selectClassmateIdByPositionId((long)25);
            System.out.println("test1");
            List<Classmate> classmates = new ArrayList<>();
            Student students = new Student();
            //班长的名字
            String position  = null;
            //通过classmate_id获取student_id

            teachClassModel.setClassId(classId);
            teachClassModel.setClassCode(classes.getCode());
            teachClassModel.setClassName(className);
            teachClassModel.setMenbernunmber(menbernunmber);
            teachClassModel.setHeadteacher(headteacher);
            System.out.println("test2");
            long banz = classmateMapper.selectBanz();
            // System.out.println("test2");
            System.out.println(banz);
            Student student = studentMapper.selectByPrimaryKey(banz);
            User user = userMapper.selectUserNameByUserId(student.getUserId());
            teachClassModel.setPosition(user.getUserName());
            teachClassModels.add(teachClassModel);
//            for(int j=0;j<classmatePositions.size();j++){
//                classmates = classmateMapper.selectStudentIdByClassmateId(classmatePositions.get(j).getClassmateId());
//                students = studentMapper.selectByPrimaryKey(classmates.get(j).getId());
//                position = userMapper.selectUserNameByUserId(students.getUserId());
//
//            }
        }
        return teachClassModels;
    }


    /**
     * 查看所授课班级学生信息(不包含历史)
     * 根据class——id
     * 能看到这个学生的学号，姓名，通信方式和照片
     * @param id
     * @return
     */
    @Override
    public List<ClassToStudentListModel> selectdelete0ClassStudent(long id) {
         List<ClassToStudentListModel> classToStudentListModels = new ArrayList<>();
            //存的是班级的学生
            ClassToStudentListModel classToStudentListModel  = new ClassToStudentListModel();	//代表一个授课班级的学生
			//↓添加修改
			List<ClassToStudentModel> classToStudentModels = new ArrayList<>();	//代表一个授课班级的学生
			//↑添加修改
            //根据班级id 找到授课所有学生
            System.out.println("classid---"+id);
            List<Student> stu = studentMapper.selectStuByClass(id);
            System.out.println("stuNos---"+stu);
            //根据班级id找到同班所有同学userId
        if(stu.size() == 0){
            return null;
        }
            List<Student> ids = studentMapper.selectIdsByClass(id);
            System.out.println("ids---"+ids);
            //同班同学的姓名
            List<User> userNames = userMapper.selectUserNamesByIds(ids);
            System.out.println("userNames---"+userNames);
            for (int j = 0; j < stu.size(); j++) {	//循环每个班级的每个学生
                ClassToStudentModel classToStudentModel = new ClassToStudentModel();	//代表一个学生
                classToStudentModel.setUserName(userNames.get(j).getUserName());
                classToStudentModel.setUserId(stu.get(j).getUserId());
                User user = userMapper.selectByPrimaryKey(stu.get(j).getUserId());
                classToStudentModel.setPiliticalId(stu.get(j).getPoliticalId());
                SecondLevelDiscipline secondLevelDiscipline = secondLevelDisciplineMapper.selectByPrimaryKey(stu.get(j).getSpecialtyId());
                classToStudentModel.setMajor(secondLevelDiscipline.getCategoryId());
                classToStudentModel.setMajorId(stu.get(j).getSpecialtyId());
                classToStudentModel.setGrade(stu.get(j).getGrade());
                classToStudentModel.setBegin_learn_date(stu.get(j).getBeginLearnDate());
                int sex = user.getUserSex();
                if(sex==2){
                    classToStudentModel.setSex("女");
                }else{
                    classToStudentModel.setSex("男");
                }
                Ecomm ecomm = ecommMapper.selectByPrimaryKey(stu.get(j).getPhoneEcommId());
                classToStudentModel.setContent(ecomm.getContent());
                PoliticalAffiliation politicalAffiliation = politicalAffiliationMapper.selectByPrimaryKey(stu.get(j).getPoliticalId());
                classToStudentModel.setPolitical(politicalAffiliation.getPolitical());
                // classToStudentModel.setPictureModel();
                classToStudentModel.setStuNo(stu.get(j).getStuNo());
                //classToStudentListModel.add(classToStudentModel);
				classToStudentModels.add(classToStudentModel);
            }
            classToStudentListModel.setClassToStudentModels(classToStudentModels);
			classToStudentListModels.add(classToStudentListModel);
        return classToStudentListModels;
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
