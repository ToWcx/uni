package edu.uni.userBaseInfo2.controller.approvalUtil;

import edu.uni.userBaseInfo2.bean.Ecomm;
import edu.uni.userBaseInfo2.bean.UserinfoApply;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 14:00
 * @Version 1.0
 */
public class EcommAU {
    private int type;

    private Ecomm ecomm;

    private UserinfoApply userinfoApply;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Ecomm getEcomm() {
        return ecomm;
    }

    public void setEcomm(Ecomm ecomm) {
        this.ecomm = ecomm;
    }

    public UserinfoApply getUserinfoApply() {
        return userinfoApply;
    }

    public void setUserinfoApply(UserinfoApply userinfoApply) {
        this.userinfoApply = userinfoApply;
    }

    @Override
    public String toString() {
        return "EcommAU{" +
                "type=" + type +
                ", ecomm=" + ecomm +
                ", userinfoApply=" + userinfoApply +
                '}';
    }
}
