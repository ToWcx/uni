package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.ApprovalMain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApprovalMainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApprovalMain record);

    int insertSelective(ApprovalMain record);

    ApprovalMain selectByPrimaryKey(Long id);
    //新增
    //判断该新增步骤规定表是否已存在, 如果不存在 ApprovalMain为null
    ApprovalMain selectByUniIdAndName(@Param("universityId") Long universityId, @Param("name") String name);

    List<ApprovalMain> selectByUniId(Long id);
    //搜索所有有效的步骤规定类别
    List<ApprovalMain> selectAllByDelete();

    int deleteByUniIdAndName(@Param("universityId") Long universityId,@Param("name") String name);

    int updateByPrimaryKeySelective(ApprovalMain record);

    int updateByPrimaryKey(ApprovalMain record);
}