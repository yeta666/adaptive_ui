package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 答案表的实体类
 * Created by yeta on 2017/5/12/012.
 */
@Entity
public class Answers implements Serializable {
    //题目编号
    @Id
    @GeneratedValue
    private Integer id;
    //可选答案1
    private String answer1;
    //可选答案2
    private String answer2;

    public Answers() {
    }

    public Answers(String answer1, String answer2) {
        this.answer1 = answer1;
        this.answer2 = answer2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
