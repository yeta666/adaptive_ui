package com.adaptive.ui.service;

import com.adaptive.ui.controller.UserTypeController;
import com.adaptive.ui.domain2.QuestionaryAnswers;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.util.UserTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

/**
 * 用户类型有关的逻辑处理类
 * Created by yeta on 2017/4/6/006.
 */
@Service
public class UserTypeService {

    private static final Logger logger = LoggerFactory.getLogger(UserTypeController.class);

    @Autowired
    private UserTypeUtil userTypeUtil;

    @Autowired
    private QuestionaryAnswersService questionaryAnswersService;

    /**
     * 通过机器学习（决策树分类方法）判断用户类型的方法
     */
    public ResultUtil getUserType(Integer userId) throws IOException, ClassNotFoundException{
        //根据userId获取userType
        QuestionaryAnswers questionaryAnswers = questionaryAnswersService.findOne(userId);
        if (questionaryAnswers != null && questionaryAnswers.getUserType() != null && !questionaryAnswers.getUserType().equals("")) {
            return new ResultUtil(true, MessageType.message1, questionaryAnswers.getUserType());

        } else {
            //根据userId获取数据
            String[] userData = userTypeUtil.getUserData(userId);
            if (userData == null || userData.length == 0) {
                logger.info("********************** " + MessageType.message3);
                throw new MyException(MessageType.message3);
            }
            //计算用户类型
            String userType = userTypeUtil.getUserType(userData);
            if(userType == null || userType.equals("")){
                logger.info("********************** " + MessageType.message4);
                throw new MyException(MessageType.message4);
            }
            return new ResultUtil(true, MessageType.message1, userType);
        }
    }

    /**
     * 通过调查表判断用户类型的方法
     */
    public ResultUtil getUserTypeByQuestionary(Integer userId, String answers) {
        //计算用户类型
        String userType = userTypeUtil.getUserTypeByQuestionary(userId, answers);
        //封装返回数据
        if(userType == null || userType.equals("")){
            logger.info("********************** " + MessageType.message4);
            throw new MyException(MessageType.message4);
        }
        return new ResultUtil(true, MessageType.message1, userType);
    }

}
