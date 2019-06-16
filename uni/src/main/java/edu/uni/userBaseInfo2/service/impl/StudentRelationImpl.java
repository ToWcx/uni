package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.StudentRelation;
import edu.uni.userBaseInfo2.mapper.StudentRelationMapper;
import edu.uni.userBaseInfo2.mapper.UserMapper;
import edu.uni.userBaseInfo2.service.StudentRelationService;
import edu.uni.userBaseInfo2.service.model.StudentRelationModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentRelationImpl implements StudentRelationService {
    @Autowired
    private StudentRelationMapper studentRelationMapper;

    @Override
    public boolean insert(StudentRelation studentRelation){
        return studentRelationMapper.insert(studentRelation)>0 ? true:false;
    }


    @Override
    public boolean delete(Long id){
        return studentRelationMapper.deleteByPrimaryKey(id)>0 ? true:false;
    }

    @Override
    public boolean update(StudentRelation studentRelation){
        return studentRelationMapper.updateByPrimaryKey(studentRelation)>0?true:false;
    }

    @Override
    public StudentRelationModel select(Long id){
        StudentRelation studentRelation = studentRelationMapper.selectByPrimaryKey(id);
        StudentRelationModel studentRelationModel=convertFromBean(studentRelation);

        return studentRelationModel;
    }

    @Override
    public List<StudentRelationModel> selectUser(Long id){
        List<StudentRelation>  studentRelations= studentRelationMapper.selectByUserId(id);
        System.out.println(studentRelations);
        List<StudentRelationModel> studentRelationModels = convertFromBean(studentRelations);
        System.out.println(studentRelationModels);
        return studentRelationModels;
    }

    @Override
    public List<StudentRelationModel> selectRela(Long id, String rela){
        int relation=0;
        if(rela.equals("母亲")){
            relation=0;
        }else if(rela.equals("父亲")){
            relation=1;
        }else if(rela.equals("哥哥")){
            relation=2;
        }else if(rela.equals("弟弟")){
            relation=3;
        }else if(rela.equals("姐姐")){
            relation=4;
        }else if(rela.equals("妹妹")){
            relation=5;
        }else{
            relation=6;
        }

        List<StudentRelation> studentRelation=studentRelationMapper.selectByRelation(id,relation);
        List<StudentRelationModel> studentRelationModel=convertFromBean(studentRelation);
        return studentRelationModel;
    }

    private List<StudentRelationModel> convertFromBean(List<StudentRelation> studentRelations){
        List<StudentRelationModel> studentRelationModels = new ArrayList<>();
        for(int i=0;i<studentRelations.size();i++){
            studentRelationModels.add(convertFromBean(studentRelations.get(i)));
        }
        return studentRelationModels;
    }

    public StudentRelationModel convertFromBean(StudentRelation studentRelations){
        StudentRelationModel studentRelationModel=new StudentRelationModel();
        if(studentRelations != null) {
            studentRelationModel.setId(studentRelations.getUserId());
            studentRelationModel.setRelaName(studentRelations.getRelaName());
            studentRelationModel.setRelaId(studentRelations.getRelaId());
            if(studentRelations.getRelationship() == 0){
                studentRelationModel.setRelationship("母亲");
            }else if(studentRelations.getRelationship() == 1){
                studentRelationModel.setRelationship("父亲");
            }else if(studentRelations.getRelationship() == 2){
                studentRelationModel.setRelationship("哥哥");
            }else if(studentRelations.getRelationship() == 3){
                studentRelationModel.setRelationship("弟弟");
            }else if(studentRelations.getRelationship() == 4){
                studentRelationModel.setRelationship("姐姐");
            }else if(studentRelations.getRelationship() == 5){
                studentRelationModel.setRelationship("妹妹");
            }else{
                studentRelationModel.setRelationship("其他");
            }
            BeanUtils.copyProperties(studentRelations, studentRelationModel);
        }
        return studentRelationModel;
    }
}
