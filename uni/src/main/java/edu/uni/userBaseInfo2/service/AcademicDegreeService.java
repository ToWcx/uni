package edu.uni.userBaseInfo2.service;

import edu.uni.userBaseInfo2.bean.AcademicDegree;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/6/5 2:52
 * @Version 1.0
 */
public interface AcademicDegreeService {
    /**
     * 新增类别
     * @param academicDegree
     * @return
     */
    boolean insert(AcademicDegree academicDegree);

    /**
     * 更新类别
     * @param academicDegree
     * @return
     */
    boolean update(AcademicDegree academicDegree);

    /**
     * 根据id查找学历程度名
     * @param id
     * @return
     */
    AcademicDegree selectById(long id);

    /**
     * 根据中文名查找学历程度id
     * @param name
     * @return
     */
    AcademicDegree selectByName(String name);

    /**
     * 查找所有类别
     * @return
     */
    List<AcademicDegree> selectAll();
}
