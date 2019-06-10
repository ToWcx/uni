package edu.uni.userBaseInfo2.controller.approvalUtil;

import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.service.model.EmployeeHistoryModel;

/**
 * @Author wuchuxin
 * @Date 2019/6/6 16:32
 * @Version 1.0
 */
public class EmployeeHistoryAU {
    private EmployeeHistoryModel employeeHistoryModel;

    private UserinfoApply userinfoApply;

    public EmployeeHistoryModel getEmployeeHistoryModel() {
        return employeeHistoryModel;
    }

    public void setEmployeeHistoryModel(EmployeeHistoryModel employeeHistoryModel) {
        this.employeeHistoryModel = employeeHistoryModel;
    }

    public UserinfoApply getUserinfoApply() {
        return userinfoApply;
    }

    public void setUserinfoApply(UserinfoApply userinfoApply) {
        this.userinfoApply = userinfoApply;
    }

    @Override
    public String toString() {
        return "EmployeeHistoryAU{" +
                "employeeHistoryModel=" + employeeHistoryModel +
                ", userinfoApply=" + userinfoApply +
                '}';
    }
}
