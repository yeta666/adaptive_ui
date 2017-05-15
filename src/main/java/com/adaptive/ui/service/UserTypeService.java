package com.adaptive.ui.service;

import com.adaptive.ui.controller.UserTypeController;
import com.adaptive.ui.domain2.UserAnswers;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.util.UserTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

/**
 * 和用户类型有关的逻辑处理类
 * Created by yeta on 2017/4/6/006.
 */
@Service
public class UserTypeService {

    private static final Logger logger = LoggerFactory.getLogger(UserTypeController.class);

    @Autowired
    private UserTypeUtil userTypeUtil;

    @Autowired
    private UserAnswersService userAnswersService;

    /**
     * 根据参数决定调用哪种计算用户类型的方法的方法
     * @param userId
     * @param answers
     * @return
     */
    public ResultUtil getUserType(Integer userId, String answers) throws IOException, ClassNotFoundException {
        //判断参数
        if(answers == null || answers.equals("")){
            //根据userId获取userType
            UserAnswers userAnswers = userAnswersService.findOne(userId);
            if (userAnswers != null && userAnswers.getUserType() != null && !userAnswers.getUserType().equals("")) {
                return new ResultUtil(true, "", userAnswers.getUserType());
            } else {
                //调用模型计算用户类型
                return this.getUserTypeByModel(userId);
            }
        }else{
            //根据用户答案计算用户类型
            return this.getUserTypeByUserAnswers(userId, answers);
        }
    }

    /**
     * 通过决策树分类方法计算用户类型的方法
     */
    public ResultUtil getUserTypeByModel(Integer userId) throws IOException, ClassNotFoundException {
        //根据userId获取数据
        String[] userData = userTypeUtil.getUserData(userId);
        if (userData == null || userData.length == 0) {
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "获取训练集失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }
        //计算用户类型
        String userType = userTypeUtil.getUserTypeByModel(userData);
        if(userType == null || userType.equals("")){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "根据模型计算用户类型失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }
        return new ResultUtil(true, "", userType);
    }

    /**
     * 通过用户答案计算用户类型的方法
     */
    public ResultUtil getUserTypeByUserAnswers(Integer userId, String answers) {
        //计算用户类型
        String userType = userTypeUtil.getUserTypeByUserAnswers(userId, answers);
        //封装返回数据
        if(userType == null || userType.equals("")){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "根据用户答案计算用户类型失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }
        return new ResultUtil(true, "", userType);
    }

}
