package edu.uni.userBaseInfo2.controller.approvalUtil;

import edu.uni.userBaseInfo2.bean.Address;
import edu.uni.userBaseInfo2.bean.UserinfoApply;
import edu.uni.userBaseInfo2.service.model.AddressModel;

/**
 * 处理前端传来的关于申请表的json数据
 * @Author wuchuxin
 * @Date 2019/5/12 9:58
 * @Version 1.0
 */
public class AddressAU {
    private int type;

    private Long id;

    private Long country;

    private Long state;

    private Long city;

    private Long area;

    private Long street;

    private String detail;

    private String zipCode;

    private String telephone;

    private Integer flag;

    private UserinfoApply userinfoApply;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getStreet() {
        return street;
    }

    public void setStreet(Long street) {
        this.street = street;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public UserinfoApply getUserinfoApply() {
        return userinfoApply;
    }

    public void setUserinfoApply(UserinfoApply userinfoApply) {
        this.userinfoApply = userinfoApply;
    }

    @Override
    public String toString() {
        return "AddressAU{" +
                "type=" + type +
                ", id=" + id +
                ", country=" + country +
                ", state=" + state +
                ", city=" + city +
                ", area=" + area +
                ", street=" + street +
                ", detail='" + detail + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", telephone='" + telephone + '\'' +
                ", flag=" + flag +
                ", userinfoApply=" + userinfoApply +
                '}';
    }
}
