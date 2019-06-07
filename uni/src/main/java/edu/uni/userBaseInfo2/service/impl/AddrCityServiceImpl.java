package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.AddrCity;
import edu.uni.userBaseInfo2.mapper.AddrCityMapper;
import edu.uni.userBaseInfo2.service.AddrCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:37
 * @Version 1.0
 */
@Service
public class AddrCityServiceImpl implements AddrCityService {
    @Autowired
    private AddrCityMapper addrCityMapper;

    @Override
    public AddrCity selectByZh(String cityZh) {
        return addrCityMapper.selectByZh(cityZh);
    }

    @Override
    public List<AddrCity> selectByStateCode(long stateCode) {
        return addrCityMapper.selectByStateCode(stateCode);
    }

    @Override
    public AddrCity selectIdByCode(Long code) {
        return addrCityMapper.selectIdByCode(code);
    }
}
