package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Picture;

import java.util.List;

public interface PictureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Picture record);

    int insertSelective(Picture record);

    Picture selectByPrimaryKey(Long id);
    //每个用户暂时只存一张照片
    Picture selectByUserId(Long id);

    int updateByPrimaryKeySelective(Picture record);

    int updateByPrimaryKey(Picture record);
}