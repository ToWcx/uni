package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.UserinfoApply;

public interface UserinfoApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserinfoApply record);

    int insertSelective(UserinfoApply record);

    UserinfoApply selectByPrimaryKey(Long id);

    UserinfoApply selectAMId(Long id);

    int update(UserinfoApply userinfoApply);

    int updateByPrimaryKeySelective(UserinfoApply record);

    int updateByPrimaryKey(UserinfoApply record);
}