package edu.uni.userBaseInfo2.controller.approvalUtil;

import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.service.model.EmployeeModel;

/**
 * @Author wuchuxin
 * @Date 2019/5/22 20:43
 * @Version 1.0
 */
public class EmployeeAU {
    private EmployeeModel employeeModel;

    private UserinfoApply userinfoApply;

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }

    public UserinfoApply getUserinfoApply() {
        return userinfoApply;
    }

    public void setUserinfoApply(UserinfoApply userinfoApply) {
        this.userinfoApply = userinfoApply;
    }

    @Override
    public String toString() {
        return "EmployeeAU{" +
                "employeeModel=" + employeeModel +
                ", userinfoApply=" + userinfoApply +
                '}';
    }
}
