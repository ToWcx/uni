package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.AddrState;
import edu.uni.userBaseInfo2.mapper.AddrStateMapper;
import edu.uni.userBaseInfo2.service.AddrStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:37
 * @Version 1.0
 */
@Service
public class AddrStateServiceImpl implements AddrStateService {
    @Autowired
    private AddrStateMapper addrStateMapper;

    @Override
    public AddrState selectByZh(String stateZh) {
        return addrStateMapper.selectByZh(stateZh);
    }

    @Override
    public List<AddrState> selectByCountryCode(long countryCode) {
        return addrStateMapper.selectByCountryCode(countryCode);
    }

    @Override
    public AddrState selectIdByCode(Long code) {
        return addrStateMapper.selectIdByCode(code);
    }
}
