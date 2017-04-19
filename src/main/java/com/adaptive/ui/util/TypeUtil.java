package com.adaptive.ui.util;

/**
 * Created by yeta on 2017/4/10/010.
 */
public class TypeUtil {
    //类型
    private String type;
    //程度
    private Integer level;

    //构造方法
    public TypeUtil(String type, Integer level) {
        this.type = type;
        this.level = level;
    }

    //getter和setter方法
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
