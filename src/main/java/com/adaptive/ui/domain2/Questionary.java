package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 调查表实体类
 * Created by yeta on 2017/4/6/006.
 */
@Entity
public class Questionary {

    //id
    @Id
    @GeneratedValue
    private Integer id;
    //问题
    private String question;
    //回答1
    private String answer1;
    //回答2
    private String answer2;

    //构造方法
    public Questionary() {
    }

    //getter和setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }
}
