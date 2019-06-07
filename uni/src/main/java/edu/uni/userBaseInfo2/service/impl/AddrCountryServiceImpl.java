package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.AddrCountry;
import edu.uni.userBaseInfo2.mapper.AddrCountryMapper;
import edu.uni.userBaseInfo2.service.AddrCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:38
 * @Version 1.0
 */
@Service
public class AddrCountryServiceImpl implements AddrCountryService {
    @Autowired
    private AddrCountryMapper addrCountryMapper;

    @Override
    public AddrCountry selectByZh(String countryZh) {
        return addrCountryMapper.selectByZh(countryZh);
    }

    @Override
    public List<AddrCountry> selectAll() {
        return addrCountryMapper.selectAll();
    }

    @Override
    public AddrCountry selectIdByCode(Long code) {
        return addrCountryMapper.selectIdByCode(code);
    }
}
