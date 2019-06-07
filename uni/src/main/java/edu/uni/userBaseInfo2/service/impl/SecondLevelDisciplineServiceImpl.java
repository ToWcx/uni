package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.SecondLevelDiscipline;
import edu.uni.userBaseInfo2.mapper.SecondLevelDisciplineMapper;
import edu.uni.userBaseInfo2.service.SecondLevelDisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wuchuxin
 * @Date 2019/5/19 9:59
 * @Version 1.0
 */
@Service
public class SecondLevelDisciplineServiceImpl implements SecondLevelDisciplineService {
    @Autowired
    private SecondLevelDisciplineMapper secondLevelDisciplineMapper;

    @Override
    public SecondLevelDiscipline selectByZh(String zh,long id) {
        return secondLevelDisciplineMapper.selectByZh(zh,id);
    }
}
