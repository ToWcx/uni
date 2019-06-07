package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.mapper.*;
import edu.uni.userBaseInfo2.service.UserService;
import edu.uni.userBaseInfo2.service.model.StudentRelationModel;
import edu.uni.userBaseInfo2.service.model.UserModel;
import edu.uni.userBaseInfo2.bean.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper ;
//    @Autowired
//    private StudentRelationMapper studentRelationMapper;

    @Override
    public boolean insert(User user) {
        return userMapper.insert(user) > 0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        return userMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean update(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

    @Override
    public List<UserModel> selectAll() {
        List<User> users = userMapper.selectAll();
        List<UserModel> userModels = new ArrayList<>();
        for(int i=0;i<users.size();i++){
            UserModel userModel = convertFromBean(users.get(i));
            userModels.add(userModel);
        }
        return userModels;
    }

    @Override
    public UserModel select(long id) {
        User user=userMapper.selectByPrimaryKey(id);
        UserModel usermodel = convertFromBean(user);
        //*********************
//        StudentRelationModel studentRelationModel = new StudentRelationModel();
//        studentRelationModel.setRelaBirthday(userMapper.selectByPrimaryKey(id).getUserBirthday().toString());
//        studentRelationModel.setRelationship(studentRelationMapper.selectByUserId(id));

        //**********************
        return usermodel;
    }

    @Override
    public User selectUniIdById(long id) {
        return userMapper.selectUniIdById(id);
    }

    private UserModel convertFromBean(User user){
        if(user == null) {
            return null;
        }
        UserModel usermodel = new UserModel();
        BeanUtils.copyProperties(user,usermodel);
        usermodel.setUniversity(String.valueOf(user.getUniversityId()));    //缺接口
        if(user.getUserSex() == 0){
            usermodel.setUserSex("女");
        } else if(user.getUserSex() == 1){
            usermodel.setUserSex("男");
        } else if(user.getUserSex() == 2){
            usermodel.setUserSex("不详");
        }
        if(user.getUserType() == 0){
            usermodel.setUserType("游客");
        } else if(user.getUserType() == 1){
            usermodel.setUserType("学生");
        } else if(user.getUserType() == 2){
            usermodel.setUserType("教职员工");
        } else if(user.getUserType() == 3){
            usermodel.setUserType("校外职员");
        } else if(user.getUserType() == 4){
            usermodel.setUserType("学生亲属");
        } else if(user.getUserType() == 5){
            usermodel.setUserType("系统运营者");
        } else if(user.getUserType() == 6){
            usermodel.setUserType("学校信息主管");
        }
        return usermodel;
    }
}
