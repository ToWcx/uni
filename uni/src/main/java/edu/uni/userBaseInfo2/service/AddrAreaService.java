package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.AddrArea;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 17:32
 * @Version 1.0
 */
public interface AddrAreaService {

    AddrArea selectByZh(String areaZh);

    List<AddrArea> selectByCityCode(long cityCode);

    AddrArea selectIdByCode(Long code);
}
