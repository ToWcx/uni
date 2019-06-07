package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.Picture;
import edu.uni.userBaseInfo2.service.model.PictureModel;

import java.util.List;

public interface PictureService {
    /**
     * 保存新类别
     * @param  picture
     * @return
     */
    boolean insert(Picture picture);

    /**
     * 删除类别
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 更新类别
     * @param picture
     * @return
     */
    boolean update(Picture picture);

    /**
     * 查找类别
     * @param type
     * @return
     */
    Picture select(long type);

    /**
     * 查找所有类别
     * @return
     */
    List<PictureModel> selectAll(long id);
}
