package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.PoliticalAffiliation;

/**
 * @Author wuchuxin
 * @Date 2019/5/19 9:46
 * @Version 1.0
 */
public interface PoliticalAffiliationService {

    /**
     * 根据政治面貌中文查找对应id
     * @param zh
     * @return
     */
    PoliticalAffiliation selectByZh(String zh);
}
