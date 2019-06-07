package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Academic;

import java.util.List;

public interface AcademicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Academic record);

    int insertSelective(Academic record);

    Academic selectByPrimaryKey(Long id);

    //新增
    Academic selectByName(String name);

    List<Academic> selectAll();

    int updateByPrimaryKeySelective(Academic record);

    int updateByPrimaryKey(Academic record);
}