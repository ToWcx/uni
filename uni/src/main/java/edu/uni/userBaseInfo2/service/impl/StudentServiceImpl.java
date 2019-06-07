package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.*;
import edu.uni.userBaseInfo2.mapper.*;
import edu.uni.userBaseInfo2.service.AddressService;
import edu.uni.userBaseInfo2.service.EcommService;
import edu.uni.userBaseInfo2.service.StudentService;
import edu.uni.userBaseInfo2.service.UserService;
import edu.uni.userBaseInfo2.service.model.ClassMateModel;

import edu.uni.userBaseInfo2.service.model.StudentModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper ;
    @Autowired
    private SecondLevelDisciplineMapper secondLevelDisciplineMapper;
    @Autowired
    private PoliticalAffiliationMapper politicalAffiliationMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean insert(Student student)  {
        return studentMapper.insert(student) > 0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        return studentMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean update(Student student) {
        return studentMapper.updateByPrimaryKeySelective(student) > 0 ? true : false;
    }

    @Override
    public boolean updateByUserId(long id) {
        return studentMapper.updateByUserId(id) > 0 ? true : false;
    }

    @Override
    public boolean updateTrueById(long id) {
        return studentMapper.updateTrueById(id) > 0 ? true : false;
    }

    /**
     * 查询学生有效基础信息
     * @param id
     * @return
     */
    @Override
    public StudentModel select(long id) {
        //测试
        Student student = studentMapper.selectByUserIdAndDelete(id);
//        System.out.println("StuServiceImpl测试Map  student:" + student);
//        List<StudentRelation> studentRelations = studentRelationMapper.selectByUserId(id);
        //照片暂不添加
        StudentModel studentModel = convertFromBean(student);
        String liveRoom = "学生宿舍紫薇苑1栋101";
        studentModel.setLiveRoom(liveRoom);
        System.out.println(studentModel);
        return studentModel;
    }

    @Override
    public Student selectBean(long id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Student> selectAll(){
        List<Student> students = studentMapper.selectAllByDeleted();
        List<StudentModel> studentModels = new ArrayList<>();
        for(int i=0;i<students.size();i++){
            StudentModel studentModel = new StudentModel();
            studentModel.setMajor(secondLevelDisciplineMapper.selectByPrimaryKey(students.get(i).getSpecialtyId()).getCategoryId());
            studentModel.setPolitical(politicalAffiliationMapper.selectByPrimaryKey(students.get(i).getPoliticalId()).getPolitical());
            String liveRoom = "学生宿舍紫薇苑1栋101";
            studentModel.setLiveRoom(liveRoom);
            studentModels.add(studentModel);
        }

        return students;
    }

//    /**
//     * 根据user_id查询学生所有信息
//     * @return
//     */
//    @Override
//    public StudentInfoModel selectInfoByUserId(long id){
//        List<Student> students = studentMapper.selectAllByDeleted();
//        StudentInfoModel studentInfoModel = new StudentInfoModel();
//
//        studentInfoModel.setAddressModels(addressService.selectAll(id));
//        studentInfoModel.setEcommModels(ecommService.selectAll(id));
//        studentInfoModel.setStudentModel(this.select(id));
//        studentInfoModel.setUserModel(null);    //缺少user接口
//        return studentInfoModel;
//    }

    /**
     * 查询学生所有历史基础信息
     * @param id
     * @return
     */
    @Override
    public List<StudentModel> selectStudentHistory(long id) {
        List<Student> students = studentMapper.selectByUserId(id);
        List<StudentModel> studentModels = new ArrayList<>();
        if(students != null){
            BeanUtils.copyProperties(students,studentModels);
            for(int i=0; i<students.size(); i++){
                StudentModel studentModel = new StudentModel();
                studentModel.setMajor(secondLevelDisciplineMapper.selectByPrimaryKey(students.get(i).getSpecialtyId()).getCategoryId());
                studentModel.setPolitical(politicalAffiliationMapper.selectByPrimaryKey(students.get(i).getPoliticalId()).getPolitical());
                String liveRoom = "学生宿舍紫薇苑1栋101";
                studentModel.setLiveRoom(liveRoom);
                studentModels.add(studentModel);
            }
        }
        return studentModels;
    }

    /**
     * 查询同班同学部分信息
     * @param id
     * @return
     */
    @Override
    public List<ClassMateModel> selectClassMate(long id) {
        Long classId = studentMapper.selectByUserIdAndDelete(id).getClassId();
        //根据班级id 找到同班所有同学姓名

        return this.selectByClassId(classId);
    }

    /**
     * 查询班级同学部分信息
     * @param id
     * @return
     */
    @Override
    public List<ClassMateModel> selectByClassId(long id) {
        //根据班级id 找到同班所有同学姓名
        System.out.println(id);
        List<Student> stuNos = studentMapper.selectStuNosByClass(id);
        System.out.println("stuNos:" + stuNos);
        //根据班级id 找到同班所有同学userId
        List<Student> ids = studentMapper.selectIdsByClass(id);
        System.out.println("ids:" + ids);
        //同班同学的姓名
        List<User> userNames = userMapper.selectUserNamesByIds(ids);
        System.out.println(userNames);
        List<ClassMateModel> classMateModels = new ArrayList<>();
        for(int i=0;i<stuNos.size();i++){
            ClassMateModel classMateModel = new ClassMateModel();
            classMateModel.setUserName(userNames.get(i).getUserName());
            classMateModel.setStuNo(stuNos.get(i).getStuNo());
            classMateModels.add(classMateModel);
        }
        System.out.println(classMateModels);

        return classMateModels;
    }

//    @Override
//    public void selectUserRelation(long id) {
//        //根据student_id(即user_id) 查找亲属信息
//        List<StudentRelation> studentRelation = studentRelationMapper.selectByUserId(id);
////        studentRelation.getRelaId();
//        //未解决
//    }

    private StudentModel convertFromBean(Student student){
        StudentModel studentModel = new StudentModel();
        if(student != null) {
            BeanUtils.copyProperties(student, studentModel);
            //根据major.id找到major的值
            String major = secondLevelDisciplineMapper.selectByPrimaryKey(student.getSpecialtyId()).getCategoryId();
            String political = politicalAffiliationMapper.selectByPrimaryKey(student.getPoliticalId()).getPolitical();
            studentModel.setMajor(major);
            studentModel.setPolitical(political);
        }
//        if(studentRelations != null){
//            List<StudentRelationModel> studentRelationModels = new ArrayList<>();
//            BeanUtils.copyProperties(studentRelations,studentRelationModels);
//            //对不同的studentRelation，给对应的Model添值
//            studentModel.setStudentRelations(studentRelationModels);
//        }
        return studentModel;
    }

}
