package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.Ecomm;
import edu.uni.userBaseInfo2.service.model.EcommModel;

import java.util.List;

public interface EcommService {
    /**
     * 保存新类别
     * @param ecomm
     * @return
     */
    boolean insert(Ecomm ecomm);

    /**
     * 删除类别
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 更新类别
     * @param ecomm
     * @return
     */
    boolean update(Ecomm ecomm);

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
     * 根据id查找通讯
     * @param id
     * @return
     */
    Ecomm select(long id);

    /**
     * 查找所有类别
     * @return
     */
    List<EcommModel> selectAll(long id);
}
