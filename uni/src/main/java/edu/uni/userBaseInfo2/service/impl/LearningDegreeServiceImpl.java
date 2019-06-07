package edu.uni.userBaseInfo2.service.impl;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import edu.uni.userBaseInfo2.bean.LearningDegree;
import edu.uni.userBaseInfo2.mapper.*;
import edu.uni.userBaseInfo2.service.LearningDegreeService;
import edu.uni.userBaseInfo2.service.model.LearningDegreeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LearningDegreeServiceImpl implements LearningDegreeService {

    @Autowired
    private LearningDegreeMapper learningDegreeMapper;
    @Autowired
    private AcademicMapper academicMapper;
    @Autowired
    private AcademicDegreeMapper academicDegreeMapper;
    @Autowired
    private AddrCountryMapper addrCountryMapper;
    @Autowired
    private AddrCityMapper addrCityMapper;

    @Override
    public boolean insert(LearningDegree learningDegree) {
        return learningDegreeMapper.insert(learningDegree)>0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        return learningDegreeMapper.deleteByPrimaryKey(id)>0 ? true:false;
    }

    @Override
    public boolean update(LearningDegree learningDegree) {
        return learningDegreeMapper.updateByPrimaryKeySelective(learningDegree) > 0 ? true:false;
    }

    @Override
    public boolean updateTrueById(Long id) {
        return learningDegreeMapper.updateTrueById(id) > 0 ? true : false;
    }

    @Override
    public boolean updateById(Long id) {
        return learningDegreeMapper.updateById(id) > 0 ? true : false;
    }

    @Override
    public LearningDegreeModel select(long type) {
        return null;
    }

    @Override
    public LearningDegree selectBean(long id) {
        return learningDegreeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<LearningDegreeModel> selectAll(long id) {
        List<LearningDegree> learningDegrees = learningDegreeMapper.selectByUserId(id);
        List<LearningDegreeModel> learningDegreeModels =convertFromBean(learningDegrees);
        System.out.println(learningDegreeModels);
        return learningDegreeModels;
    }

    private List<LearningDegreeModel> convertFromBean(List<LearningDegree> learningDegrees){
        List<LearningDegreeModel> learningDegreeModels = new ArrayList<>();
        for(int i = 0 ; i<learningDegrees.size();i++){
            LearningDegreeModel learningDegreeModel = new LearningDegreeModel();
            BeanUtils.copyProperties(learningDegrees.get(i),learningDegreeModel);
            learningDegreeModel.setCountry(addrCountryMapper.selectByPrimaryKey(learningDegrees.get(i).getCountryId()).getCountryZh());
            learningDegreeModel.setCity(addrCityMapper.selectByPrimaryKey(learningDegrees.get(i).getCityId()).getCityZh());
            learningDegreeModel.setSchool(learningDegrees.get(i).getSchoolId().toString());
            learningDegreeModel.setAcademic(academicMapper.selectByPrimaryKey(learningDegrees.get(i).getAcademicId()).getName());
            learningDegreeModel.setDegree(academicDegreeMapper.selectByPrimaryKey(learningDegrees.get(i).getDegreeId()).getName());
//            learningDegreeModel.setBeginTime(learningDegrees.get(i).getBeginTime());
//            learningDegreeModel.setEndTime(learningDegrees.get(i).getEndTime());
            learningDegreeModels.add(learningDegreeModel);
        }
        return learningDegreeModels;
    }
}
