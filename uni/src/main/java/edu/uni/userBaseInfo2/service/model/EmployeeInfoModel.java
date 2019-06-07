package edu.uni.userBaseInfo2.service.model;

public class EmployeeInfoModel {
    private AddressModel addressModel;
    private EcommModel ecommModel;
    private EmployeeModel employeeModel;
    private UserModel userModel;
    private LearningDegreeModel learningDegreeModel;
    private EmployeeHistoryModel employeeHistoryModel;

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public EcommModel getEcommModel() {
        return ecommModel;
    }

    public void setEcommModel(EcommModel ecommModel) {
        this.ecommModel = ecommModel;
    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public LearningDegreeModel getLearningDegreeModel() {
        return learningDegreeModel;
    }

    public void setLearningDegreeModel(LearningDegreeModel learningDegreeModel) {
        this.learningDegreeModel = learningDegreeModel;
    }

    public EmployeeHistoryModel getEmployeeHistoryModel() {
        return employeeHistoryModel;
    }

    public void setEmployeeHistoryModel(EmployeeHistoryModel employeeHistoryModel) {
        this.employeeHistoryModel = employeeHistoryModel;
    }

    @Override
    public String toString() {
        return "EmployeeInfoModel{" +
                "addressModel=" + addressModel +
                ", ecommModel=" + ecommModel +
                ", employeeModel=" + employeeModel +
                ", userModel=" + userModel +
                ", learningDegreeModel=" + learningDegreeModel +
                ", employeeHistoryModel=" + employeeHistoryModel +
                '}';
    }
}
