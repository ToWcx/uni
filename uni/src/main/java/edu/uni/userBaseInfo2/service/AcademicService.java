package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.Academic;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/6/5 2:41
 * @Version 1.0
 */
public interface AcademicService {
    /**
     * 新增类别
     * @param academic
     * @return
     */
    boolean insert(Academic academic);

    /**
     * 更新类别
     * @param academic
     * @return
     */
    boolean update(Academic academic);

    /**
     * 根据id查找学历名
     * @param id
     * @return
     */
    Academic selectById(long id);

    /**
     * 根据中文名查找学历id
     * @param name
     * @return
     */
    Academic selectByName(String name);

    /**
     * 查找所有类别
     * @return
     */
    List<Academic> selectAll();
}
