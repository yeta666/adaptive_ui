package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * train_array_attribute表的实体类
 * Created by yeta on 2017/4/20/020.
 */
@Entity
public class TrainArrayAttributes {
    //id
    @Id
    @GeneratedValue
    private Integer id;

    //attributes
    private String attributes;

    //模型类型
    private String modelType;

    //构造方法
    public TrainArrayAttributes() {
    }

    public TrainArrayAttributes(String attributes, String modelType) {
        this.attributes = attributes;
        this.modelType = modelType;
    }

    //getter和setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }
}
