package com.adaptive.ui.service;

import com.adaptive.ui.controller.UserTypeController;
import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.util.UserTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 用户类型有关的逻辑处理类
 * Created by yeta on 2017/4/6/006.
 */
@Service
public class UserTypeService {

    private static final Logger logger = LoggerFactory.getLogger(UserTypeController.class);

    @Autowired
    private UserTypeUtil userTypeUtil;

    /**
     * 通过机器学习（决策树分类方法）判断用户类型的方法
     */
    public ResultUtil getUserType(Integer userId) throws IOException, ClassNotFoundException{
        if(userId == null){
            return new ResultUtil(false, "userId为空，无法获取数据！", null);
        }
        //根据userId获取数据
        String[] data = userTypeUtil.getUserData(userId);
        if(data.length == 0){
            return new ResultUtil(false, "未获取到该用户的数据！", null);
        }
        //计算用户类型
        //String userType = userTypeUtil.getUserType(data);
        //封装返回数据
        return new ResultUtil(false, "", data);
    }

    /**
     * 通过调查表判断用户类型的方法
     */
    public ResultUtil getUserTypeByQuestionary(String answers) {
        //计算用户类型
        String userType = userTypeUtil.getUserTypeByQuestionary(answers);
        //封装返回数据
        return new ResultUtil(true, "", userType);
    }

}
