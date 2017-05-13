package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 与用户答案表对应的实体类
 * Created by yeta on 2017/4/25/025.
 */
@Entity
public class UserAnswers {
    //用户id
    @Id
    private Integer userId;
    //所有答案组成的字符串，“,”分隔
    private String answers;
    //用户类型
    private String userType;

    //构造方法
    public UserAnswers() {
    }

    //getter和setter方法
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
