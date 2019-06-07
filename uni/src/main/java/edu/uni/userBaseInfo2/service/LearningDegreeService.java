package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.LearningDegree;
import edu.uni.userBaseInfo2.service.model.LearningDegreeModel;

import java.util.List;

public interface LearningDegreeService {
    /**
     * 保存新类别
     * @param  learningDegree
     * @return
     */
    boolean insert(LearningDegree learningDegree);

    /**
     * 删除类别
     * @param id
     * @return
     */
    boolean delete(long id);

    /**
     * 更新类别
     * @param learningDegree
     * @return
     */
    boolean update(LearningDegree learningDegree);

    /**
     * 将记录置为有效
     * @param id
     * @return
     */
    boolean updateTrueById(Long id);

    /**
     * 逻辑删除旧记录
     * @param id
     * @return
     */
    boolean updateById(Long id);

    /**
     * 查找类别
     * @param type
     * @return
     */
    LearningDegreeModel select(long type);

    /**
     * 根据表id查找bean
     * @param id
     * @return
     */
    LearningDegree selectBean(long id);

    /**
     * 查找所有类别
     * @return
     */
    List<LearningDegreeModel> selectAll(long id);
}
