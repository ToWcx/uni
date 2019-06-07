package edu.uni.userBaseInfo2.service.model;

public class EcommModel {

    private Long id;

    private String content;

    private Integer flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "EcommModel{" +
                "id=" + id +
                "content='" + content + '\'' +
                ", flag=" + flag +
                '}';
    }
}
