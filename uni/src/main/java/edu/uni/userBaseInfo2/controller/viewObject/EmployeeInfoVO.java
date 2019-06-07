package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.AddressModel;
import edu.uni.userBaseInfo2.service.model.EcommModel;
import edu.uni.userBaseInfo2.service.model.EmployeeModel;
import edu.uni.userBaseInfo2.service.model.UserModel;

import java.util.List;

public class EmployeeInfoVO {
    private UserModel userModel;

    private EmployeeModel employeeModel ;

    private List<AddressModel> addressModels;

    private List<EcommModel> ecommModels;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }

    public List<AddressModel> getAddressModels() {
        return addressModels;
    }

    public void setAddressModels(List<AddressModel> addressModels) {
        this.addressModels = addressModels;
    }

    public List<EcommModel> getEcommModels() {
        return ecommModels;
    }

    public void setEcommModels(List<EcommModel> ecommModels) {
        this.ecommModels = ecommModels;
    }

    @Override
    public String toString() {
        return "EmployeeInfoVO{" +
                "userModel=" + userModel +
                ", employeeModel=" + employeeModel +
                ", addressModels=" + addressModels +
                ", ecommModels=" + ecommModels +
                '}';
    }
}
