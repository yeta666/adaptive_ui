package com.adaptive.ui.service;

import com.adaptive.ui.domain1.User;
import com.adaptive.ui.repositary1.UserRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 与user表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class UserService {

    @Autowired
    private UserRepositary userRepositary;

    /**
     * 根据userId获取一条用户数据
     * @param userId
     * @return
     */
    public User findOne(Integer userId){
        return userRepositary.findOne(userId);
    }
}
