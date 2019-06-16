package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.EcommModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/6/16 17:33
 * @Version 1.0
 */
public class HeadMasterVO {
    private String name;

    private String subdepartment;

    private List<EcommModel> ecommModels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(String subdepartment) {
        this.subdepartment = subdepartment;
    }

    public List<EcommModel> getEcommModels() {
        return ecommModels;
    }

    public void setEcommModels(List<EcommModel> ecommModels) {
        this.ecommModels = ecommModels;
    }

    @Override
    public String toString() {
        return "HeadMasterVO{" +
                "name='" + name + '\'' +
                ", subdepartment='" + subdepartment + '\'' +
                ", ecommModels=" + ecommModels +
                '}';
    }
}
