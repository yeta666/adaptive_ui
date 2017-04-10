package com.adaptive.ui.util;

import com.adaptive.ui.domain.Questionary;
import com.adaptive.ui.domain.Result;
import com.adaptive.ui.domain.User;
import com.adaptive.ui.repositary.UserRepositary;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeta on 2017/4/6/006.
 * 通过机器学习方法计算用户类型的方法
 */
@Component
public class JudgeUserType {

    private static final Logger logger = LoggerFactory.getLogger(JudgeUserType.class);

    @Autowired
    private UserRepositary userRepositary;

    /**
     * 通过机器学习方法计算用户类型
     * @param userId
     * @return
     */
    public Result judgeUserType(Integer userId){
        User user = userRepositary.findOne(userId);
        if(user != null) {
            if(user.getUserGender().equals("女")){
                return new Result(true, "", Type.TYPE1);
            }else if(user.getUserGender().equals("男")){
                return new Result(true, "", Type.TYPE2);
            }else{
                return new Result(false, "数据库中没有该用户性别信息！", null);
            }
        }else{
            return new Result(false, "数据库中没有该用户任何信息！", null);
        }
    }

    public Result judgeUserTypeByQuestionary(String answers) {
        //转换数据格式
        List answersList = (List)JSON.parse(answers);
        //计算用户类型
        //测试


        return null;
    }
}
