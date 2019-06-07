package edu.uni.userBaseInfo2.service.impl;

import edu.uni.userBaseInfo2.bean.AddrArea;
import edu.uni.userBaseInfo2.mapper.AddrAreaMapper;
import edu.uni.userBaseInfo2.service.AddrAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:37
 * @Version 1.0
 */
@Service
public class AddrAreaServiceImpl implements AddrAreaService {
    @Autowired
    private AddrAreaMapper addrAreaMapper;

    @Override
    public AddrArea selectByZh(String areaZh) {
        return addrAreaMapper.selectByZh(areaZh);
    }

    @Override
    public List<AddrArea> selectByCityCode(long cityCode) {
        return addrAreaMapper.selectByCityCode(cityCode);
    }

    @Override
    public AddrArea selectIdByCode(Long code) {
        return addrAreaMapper.selectIdByCode(code);
    }
}
