package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Picture;

import java.util.List;

public interface PictureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(Long id);

    //新增查询： 根据user_id查找该用户所有地址信息
    List<Picture> selectByUserId(Long id);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);
}