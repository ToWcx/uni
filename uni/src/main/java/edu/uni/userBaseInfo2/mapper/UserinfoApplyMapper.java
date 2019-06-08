package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.UserinfoApply;

import java.util.List;

public interface UserinfoApplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserinfoApply record);

    int insertSelective(UserinfoApply record);

    UserinfoApply selectByPrimaryKey(Long id);

    UserinfoApply selectAMId(Long id);

    //根据byWho查找
    List<UserinfoApply> selectByUserId(Long id);

    int update(UserinfoApply userinfoApply);

    int updateByPrimaryKeySelective(UserinfoApply record);

    int updateByPrimaryKey(UserinfoApply record);
}