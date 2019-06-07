package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.SecondLevelDiscipline;

/**
 * @Author wuchuxin
 * @Date 2019/5/19 9:57
 * @Version 1.0
 */
public interface SecondLevelDisciplineService {

    /**
     * 根据二级学科中文及一级学科id查找对应二级学科id
     * @param zh
     * @return
     */
    SecondLevelDiscipline selectByZh(String zh,long id);

//    SecondLevelDiscipline select();
}
