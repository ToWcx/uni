package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.PoliticalAffiliation;
import edu.uni.userBaseInfo2.mapper.PoliticalAffiliationMapper;
import edu.uni.userBaseInfo2.service.PoliticalAffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wuchuxin
 * @Date 2019/5/19 9:47
 * @Version 1.0
 */
@Service
public class PoliticalAffiliationServiceImpl implements PoliticalAffiliationService {
    @Autowired
    private PoliticalAffiliationMapper politicalAffiliationMapper;

    @Override
    public PoliticalAffiliation selectByZh(String zh) {
        return politicalAffiliationMapper.selectByZh(zh);
    }
}
