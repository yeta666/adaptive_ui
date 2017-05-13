package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 与treeModel表对应的实体类
 * Created by yeta on 2017/4/17/017.
 */
@Entity
public class Model {
    //id
    @Id
    @GeneratedValue
    private Integer id;
    //模型
    private byte[] model;
    //模型类别
    private String type;
    //创建时间
    private Date createDate;

    //构造方法
    public Model() {
    }

    public Model(byte[] model, String type, Date createDate) {
        this.model = model;
        this.type = type;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getModel() {
        return model;
    }

    public void setModel(byte[] model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
