package com.adaptive.ui.service;

import com.adaptive.ui.controller.UserTypeController;
import com.adaptive.ui.domain2.UserAnswers;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.type.ModelType;
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

    @Autowired
    private TrainArrayAttributesService trainArrayAttributesService;

    @Autowired
    private ModelService modelService;

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
     * 通过决策树模型计算用户类型
     * @param userId
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ResultUtil getUserTypeByModel(Integer userId) throws IOException, ClassNotFoundException {

        //根据用户id获取用户数据
        String[] userData = userTypeUtil.getUserData(userId);
        if (userData == null || userData.length == 0) {
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "根据模型计算用户类型前获取用户数据失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }

        //获取训练集属性
        String[] attributesArray = trainArrayAttributesService.getTrainArrayAttributes(ModelType.TYPE1);
        if (attributesArray == null || attributesArray.length == 0) {
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "根据模型计算用户类型前获取训练集属性失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }

        //获取决策树模型
        TreeNode tree = modelService.getModel();
        if (tree == null) {
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "根据模型计算用户类型前获取模型失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }

        //调用计算用户类型的方法
        modelService.getUserTypeByModel(tree, userData, attributesArray);

        //调用获取用户类型的方法
        String userType = modelService.getUserType();
        if(userType == null || userType.equals("")){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "获取根据模型计算之后的用户类型失败！");
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
