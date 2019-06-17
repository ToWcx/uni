package edu.uni.userBaseInfo2.service.model;

public class EmployeeListModel {
    private String userName;
    private Long userId;
    private String university;
    private Long universityId;
    private String department;
    private Long departmentId;
    private String subdepartment;
    private Long subdepartmentId;
    private String empNo;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getSubdepartment() {
        return subdepartment;
    }

    public void setSubdepartment(String subdepartment) {
        this.subdepartment = subdepartment;
    }

    public Long getSubdepartmentId() {
        return subdepartmentId;
    }

    public void setSubdepartmentId(Long subdepartmentId) {
        this.subdepartmentId = subdepartmentId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    @Override
    public String toString() {
        return "EmployeeListModel{" +
                "userName='" + userName + '\'' +
                ", userId=" + userId +
                ", university='" + university + '\'' +
                ", universityId=" + universityId +
                ", department='" + department + '\'' +
                ", departmentId=" + departmentId +
                ", subdepartment='" + subdepartment + '\'' +
                ", subdepartmentId=" + subdepartmentId +
                ", empNo='" + empNo + '\'' +
                '}';
    }
}
