package com.adaptive.ui.service;

import com.adaptive.ui.controller.UserTypeController;
import com.adaptive.ui.domain2.UserAnswers;
import com.adaptive.ui.domain2.UserType;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.repository2.UserTypeRepository;
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
    private UserTypeRepository userTypeRepository;

    /**
     * 根据参数决定调用哪种计算用户类型的方法的方法
     * @param userId
     * @param answers
     * @return
     */
    public ResultUtil getUserType(Integer userId, String answers) throws IOException, ClassNotFoundException {
        //判断参数
        if(answers == null || answers.equals("")){
            //根据userId从userAnswers表中获取userType
            UserAnswers userAnswers = userAnswersService.findOne(userId);
            if (userAnswers != null && userAnswers.getUserType() != null && !userAnswers.getUserType().equals("")) {
                return new ResultUtil(true, "", userAnswers.getUserType());
            } else {
                //根据userId从user_type表中获取userType
                UserType userType = userTypeRepository.findOne(userId);
                if(userType != null && !userType.getUserType().equals("")){
                    return new ResultUtil(true, "", userType.getUserType());
                }else{
                    return new ResultUtil(false, "没有获取到用户类型", null);
                }
            }
        }else{
            //根据用户答案计算用户类型
            return this.getUserTypeByUserAnswers(userId, answers);
        }
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

    /**
     * 根据userId获取用户类型的方法
     * @param userId
     * @return
     */
    public UserType findOne(Integer userId){
        return userTypeRepository.findOne(userId);
    }

    /**
     * 根据userId删除用户类型的方法
     * @param userId
     */
    public void delete(Integer userId){
        userTypeRepository.delete(userId);
    }

    /**
     * 保存一条用户答案数据的方法
     * @param userType
     * @return
     */
    public UserType save(UserType userType){
        return userTypeRepository.save(userType);
    }

}
