package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.Address;
import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.service.model.AddressModel;
import edu.uni.userBaseInfo2.service.model.StudentModel;

import java.util.List;

public interface AddressService {
    /**
     * 保存新类别
     * @param address
     * @return
     */
    boolean insert(Address address);

    /**
     * 删除类别
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 更新类别
     * @param address
     * @return
     */
    boolean update(Address address);

    /**
     * 根据id查询将记录置为有效
     * @param id
     * @return
     */
    boolean updateTrueById(long id);

//    /**
//     * 根据userId和flag逻辑删除地址记录
//     * @param id
//     * @param flag
//     * @return
//     */
//    boolean updateByUserIdAndFlag(long id, int flag);

    /**
     * 根据表id 逻辑删除地址记录
     * @param id
     * @return
     */
    boolean updateById(long id);

    /**
     * 根据id查找地址记录
     * @param id
     * @return
     */
    Address select(long id);

    /**
     * 根据flag查找对应类别
     * @param flag
     * @return
     */
    AddressModel selectByFlag(int flag);

    /**
     *
     * @param userId
     * @param flag
     * @return
     */
    Address selectByUserIdAndFlag(long userId, int flag);

    /**
     * 查找所有类别
     * @return
     */
    List<AddressModel> selectAll(long id);



}
