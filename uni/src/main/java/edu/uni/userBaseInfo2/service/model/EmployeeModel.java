package edu.uni.userBaseInfo2.service.model;

public class EmployeeModel {
    private Long id;

    private Long userId;

    private String empNo;

    private String department;

    private String subdepartment;

    private String discipline;

    private Long employHistoryId;

    private String political;

    private String position;

    private Long homeAddressId;

    private Long phoneEcommId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEmployHistoryId() {
        return employHistoryId;
    }

    public void setEmployHistoryId(Long employHistoryId) {
        this.employHistoryId = employHistoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(String subdepartment) {
        this.subdepartment = subdepartment;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getHomeAddressId() {
        return homeAddressId;
    }

    public void setHomeAddressId(Long homeAddressId) {
        this.homeAddressId = homeAddressId;
    }

    public Long getPhoneEcommId() {
        return phoneEcommId;
    }

    public void setPhoneEcommId(Long phoneEcommId) {
        this.phoneEcommId = phoneEcommId;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "id=" + id +
                ", empNo='" + empNo + '\'' +
                ", department='" + department + '\'' +
                ", subdepartment='" + subdepartment + '\'' +
                ", discipline='" + discipline + '\'' +
                ", political='" + political + '\'' +
                ", position='" + position + '\'' +
                ", homeAddressId=" + homeAddressId +
                ", phoneEcommId=" + phoneEcommId +
                '}';
    }
}
