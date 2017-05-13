package com.adaptive.ui.domain2;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 题目表的实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class Question implements Serializable {

    //题目编号
    @Id
    @GeneratedValue
    private Integer id;
    //内容
    private String content;
    //答案
    private Integer answersId;

    //构造方法
    public Question() {
    }

    public Question(String content, Integer answersId) {
        this.content = content;
        this.answersId = answersId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAnswersId() {
        return answersId;
    }

    public void setAnswersId(Integer answersId) {
        this.answersId = answersId;
    }
}
