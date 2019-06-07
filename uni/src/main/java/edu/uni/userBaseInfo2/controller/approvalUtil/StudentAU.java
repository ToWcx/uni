package edu.uni.userBaseInfo2.controller.approvalUtil;

import edu.uni.userBaseInfo2.bean.Student;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.service.model.StudentModel;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 21:46
 * @Version 1.0
 */
public class StudentAU {
    private StudentModel studentModel;

    private UserinfoApply userinfoApply;

    public StudentModel getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(StudentModel studentModel) {
        this.studentModel = studentModel;
    }

    public UserinfoApply getUserinfoApply() {
        return userinfoApply;
    }

    public void setUserinfoApply(UserinfoApply userinfoApply) {
        this.userinfoApply = userinfoApply;
    }

    @Override
    public String toString() {
        return "StudentAU{" +
                "studentModel=" + studentModel +
                ", userinfoApply=" + userinfoApply +
                '}';
    }
}
