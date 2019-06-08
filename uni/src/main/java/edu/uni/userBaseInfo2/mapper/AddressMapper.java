package edu.uni.userBaseInfo2.mapper;

import edu.uni.userBaseInfo2.bean.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    //根据user_id查找该用户所有地址信息（包括历史）
    List<Address> selectByUserId(Long id);

    List<Address> selectByUserIdAndDelete(Long id);
    //根据userId和flag唯一确定一条地址信息，若已存在该flag，还需把旧记录删除
//    int deleteByUserIdAndFlag(Long id,Integer flag);
    //根据userId和flag唯一确定一条地址信息，若已存在该flag，还需把旧记录删除
    int updateByUserIdAndFlag(Long id,Integer flag);

    int updateTrueById(Long id);

    int updateById(Long id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    Address selectByUserIdAndFlag(@Param("id") Long id, @Param("flag") Integer flag);

}