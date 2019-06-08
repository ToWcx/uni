package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Ecomm;

import java.util.List;

public interface EcommMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ecomm record);

    int insertSelective(Ecomm record);

    Ecomm selectByPrimaryKey(Long id);

    //新增查询： 根据user_id查找该用户所有历史电子通讯信息
    List<Ecomm> selectByUserId(Long id);
    //查找有效电子通讯信息
    List<Ecomm> selectByUserIdAndDelete(Long id);
    //根据userId和flag唯一确定一条通讯信息，若已存在该flag，还需把旧记录删除
    int updateByUserIdAndFlag(Long id,Integer flag);

    int updateTrueById(Long id);

    int updateById(Long id);

    int updateByPrimaryKeySelective(Ecomm record);

    int updateByPrimaryKey(Ecomm record);
}