package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.Academic;
import edu.uni.userBaseInfo2.mapper.AcademicMapper;
import edu.uni.userBaseInfo2.service.AcademicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/6/5 2:42
 * @Version 1.0
 */
@Service
public class AcademicServiceImpl implements AcademicService {

    @Autowired
    private AcademicMapper academicMapper;

    @Override
    public boolean insert(Academic academic) {
        return false;
    }

    @Override
    public boolean update(Academic academic) {
        return false;
    }

    @Override
    public Academic selectById(long id) {
        return academicMapper.selectByPrimaryKey(id);
    }

    @Override
    public Academic selectByName(String name) {
        return academicMapper.selectByName(name);
    }

    @Override
    public List<Academic> selectAll() {
        return academicMapper.selectAll();
    }
}
