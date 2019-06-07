package edu.uni.userBaseInfo2.service.model;

/**
 * @Author wuchuxin
 * @Date 2019/5/18 20:00
 * @Version 1.0
 */
public class AddrModel {
    private Long id;

    private Long code;

    private String addrZh;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getAddrZh() {
        return addrZh;
    }

    public void setAddrZh(String addrZh) {
        this.addrZh = addrZh;
    }
}
