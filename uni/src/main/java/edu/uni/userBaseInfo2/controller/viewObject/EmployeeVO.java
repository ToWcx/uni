package edu.uni.userBaseInfo2.controller.viewObject;

public class EmployeeVO {
    private Long id;

    private String empNo;

    private String department;

    private String subdepartment;

    private String discipline;

    private String position;

    private String political;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    @Override
    public String toString() {
        return "EmployeeVO{" +
                "id=" + id +
                ", empNo='" + empNo + '\'' +
                ", department='" + department + '\'' +
                ", subdepartment='" + subdepartment + '\'' +
                ", discipline='" + discipline + '\'' +
                ", position='" + position + '\'' +
                ", political='" + political + '\'' +
                '}';
    }
}
