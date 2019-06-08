package edu.uni.userBaseInfo2.service.model;

/**
 * 每一申请步数及审核结果
 * @Author wuchuxin
 * @Date 2019/5/16 23:37
 * @Version 1.0
 */
public class ApprovalStepModel {

    private long check_who;

    private int step;

//    private boolean result;
    private int result;

    private String reason;

    public long getCheck_who() {
        return check_who;
    }

    public void setCheck_who(long check_who) {
        this.check_who = check_who;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ApprovalStepModel{" +
                "check_who=" + check_who +
                ", step=" + step +
                ", result=" + result +
                ", reason='" + reason + '\'' +
                '}';
    }
}
