package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.AcademicDegree;
import edu.uni.userBaseInfo2.mapper.AcademicDegreeMapper;
import edu.uni.userBaseInfo2.mapper.AcademicMapper;
import edu.uni.userBaseInfo2.service.AcademicDegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/6/5 2:52
 * @Version 1.0
 */
@Service
public class AcademicDegreeServiceImpl implements AcademicDegreeService {

    @Autowired
    private AcademicDegreeMapper academicDegreeMapper;

    @Override
    public boolean insert(AcademicDegree academicDegree) {
        return false;
    }

    @Override
    public boolean update(AcademicDegree academicDegree) {
        return false;
    }

    @Override
    public AcademicDegree selectById(long id) {
        return academicDegreeMapper.selectByPrimaryKey(id);
    }

    @Override
    public AcademicDegree selectByName(String name) {
        return academicDegreeMapper.selectByName(name);
    }

    @Override
    public List<AcademicDegree> selectAll() {
        return academicDegreeMapper.selectAll();
    }
}
