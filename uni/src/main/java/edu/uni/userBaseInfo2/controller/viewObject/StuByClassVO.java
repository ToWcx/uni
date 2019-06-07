package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.ClassMateModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/15 16:23
 * @Version 1.0
 */
public class StuByClassVO {
    private Long classId;

    private List<ClassMateModel> classMateModels;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public List<ClassMateModel> getClassMateModels() {
        return classMateModels;
    }

    public void setClassMateModels(List<ClassMateModel> classMateModels) {
        this.classMateModels = classMateModels;
    }

    @Override
    public String toString() {
        return "StuByClassVO{" +
                "classId=" + classId +
                ", classMateModels=" + classMateModels +
                '}';
    }
}
