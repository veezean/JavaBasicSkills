package com.veezean.skills.stream;

/**
 * <类功能简要描述>
 *
 * @author 架构悟道
 * @since 2022/7/10
 */
public class Dept {
    private int id;

    public Dept(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                '}';
    }
}
