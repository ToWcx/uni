package edu.uni.userBaseInfo2.controller.viewObject;

import edu.uni.userBaseInfo2.service.model.UserinfoApplyModel;

import java.util.List;

/**
 * 用户审批结果中心显示的内容
 * @Author wuchuxin
 * @Date 2019/5/11 16:10
 * @Version 1.0
 */
public class UserinfoApplyVO {
    private Long id;

    private List<UserinfoApplyModel> userinfoApplyModels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserinfoApplyModel> getUserinfoApplyModels() {
        return userinfoApplyModels;
    }

    public void setUserinfoApplyModels(List<UserinfoApplyModel> userinfoApplyModels) {
        this.userinfoApplyModels = userinfoApplyModels;
    }

    @Override
    public String toString() {
        return "UserinfoApplyVO{" +
                "id=" + id +
                ", userinfoApplyModels=" + userinfoApplyModels +
                '}';
    }
}
