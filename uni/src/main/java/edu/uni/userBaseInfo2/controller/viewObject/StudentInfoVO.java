package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.AddressModel;
import edu.uni.userBaseInfo2.service.model.EcommModel;
import edu.uni.userBaseInfo2.service.model.StudentModel;
import edu.uni.userBaseInfo2.service.model.UserModel;

import java.util.List;

/**
 * @Author wuchuxin
 * @Date 2019/5/14 16:22
 * @Version 1.0
 */
public class StudentInfoVO {
    private UserModel userModel;

    private StudentModel studentModel;

    private List<EcommModel> ecommModels;

    private List<AddressModel> addressModels;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public StudentModel getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    public List<EcommModel> getEcommModels() {
        return ecommModels;
    }

    public void setEcommModels(List<EcommModel> ecommModels) {
        this.ecommModels = ecommModels;
    }

    public List<AddressModel> getAddressModels() {
        return addressModels;
    }

    public void setAddressModels(List<AddressModel> addressModels) {
        this.addressModels = addressModels;
    }

    @Override
    public String toString() {
        return "StudentInfoModel{" +
                "userModel=" + userModel +
                ", studentModel=" + studentModel +
                ", ecommModels=" + ecommModels +
                ", addressModels=" + addressModels +
                '}';
    }
}
