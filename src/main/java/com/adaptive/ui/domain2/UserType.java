package com.adaptive.ui.domain2;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 与user_type类有关的实体类
 * Created by yeta on 2017/5/29/029.
 */
@Entity
public class UserType {
    //userId
    @Id
    private Integer userId;

    //userType
    private String userType;

    public UserType() {
    }

    public UserType(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
