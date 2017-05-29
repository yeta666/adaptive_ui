package com.adaptive.ui.service;

import com.adaptive.ui.domain1.User;
import com.adaptive.ui.repository1.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与user表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据userId获取一条用户数据
     * @param userId
     * @return
     */
    public User findOne(Integer userId){
        return userRepository.findOne(userId);
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAll(){
        return userRepository.findAll();
    }
}
