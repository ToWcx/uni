package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.AddrStreet;
import edu.uni.userBaseInfo2.mapper.AddrStreetMapper;
import edu.uni.userBaseInfo2.service.AddrStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:37
 * @Version 1.0
 */
@Service
public class AddrStreetServiceImpl implements AddrStreetService {
    @Autowired
    private AddrStreetMapper addrStreetMapper;

    @Override
    public AddrStreet selectByZh(String streetZh) {
        return addrStreetMapper.selectByZh(streetZh);
    }

    @Override
    public List<AddrStreet> selectByAreaCode(long areaCode) {
        return addrStreetMapper.selectByAreaCode(areaCode);
    }

    @Override
    public AddrStreet selectIdByCode(Long code) {
        return addrStreetMapper.selectIdByCode(code);
    }
}
