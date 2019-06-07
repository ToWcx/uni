package edu.uni.userBaseInfo2.controller.approvalUtil;

import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.service.model.LearningDegreeModel;

/**
 * @Author wuchuxin
 * @Date 2019/6/5 2:28
 * @Version 1.0
 */
public class LearningDegreeAU {
    private LearningDegreeModel learningDegreeModel;

    private UserinfoApply userinfoApply;

    public LearningDegreeModel getLearningDegreeModel() {
        return learningDegreeModel;
    }

    public void setLearningDegreeModel(LearningDegreeModel learningDegreeModel) {
        this.learningDegreeModel = learningDegreeModel;
    }

    public UserinfoApply getUserinfoApply() {
        return userinfoApply;
    }

    public void setUserinfoApply(UserinfoApply userinfoApply) {
        this.userinfoApply = userinfoApply;
    }

    @Override
    public String toString() {
        return "LearningDegreeAU{" +
                "learningDegreeModel=" + learningDegreeModel +
                ", userinfoApply=" + userinfoApply +
                '}';
    }
}
