package com.veezean.skills.optional;

/**
 * 正文内容
 *
 * @author 架构悟道
 * @since 2022/7/10
 */
public class Content {
    private String id;
    private String value;

    public Content(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id='" + id + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
