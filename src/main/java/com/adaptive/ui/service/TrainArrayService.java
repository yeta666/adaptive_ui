package com.adaptive.ui.service;

import com.adaptive.ui.domain2.TrainArray;
import com.adaptive.ui.domain2.UserAnswers;
import com.adaptive.ui.repository2.TrainArrayRepository;
import com.adaptive.ui.util.UserTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 与训练集有关的逻辑处理类
 * Created by yeta on 2017/4/19/019.
 */
@Service
public class TrainArrayService {

    private static final Logger logger = LoggerFactory.getLogger(TrainArrayService.class);

    @Autowired
    private TrainArrayRepository trainArrayRepository;

    @Autowired
    private UserAnswersService userAnswersService;

    @Autowired
    private UserTypeUtil userTypeUtil;

    /**
     * 保存一条训练集数据的方法
     * @param trainArray
     * @return
     */
    public TrainArray save(TrainArray trainArray){
        return trainArrayRepository.save(trainArray);
    }

    /**
     * 获取训练集的方法
     * @return
     */
    public Object[] getTrainArrays(){
        //从数据库中获取填写调查表的用户id
        List<UserAnswers> userAnswersList = userAnswersService.findAll();
        if(userAnswersList == null || userAnswersList.size() == 0){
            logger.info("获取训练集时从数据库获取用户id失败！");
            return null;
        }
        //根据用户id数初始化训练集长度
        Object[] trainArrays = new Object[userAnswersList.size()];
        //根据用户id从原个性化网络教学系统的数据库中获取用户数据
        for(int i = 0; i < userAnswersList.size(); i++){
            //获取一个userId
            UserAnswers userAnswers = userAnswersList.get(i);
            Integer userId = userAnswers.getUserId();
            if(userId == null || userId.equals("")){
                logger.info("获取训练集时获取到一条错误用户id数据");
                continue;
            }
            //根据userId获取训练集数据
            String[] userDataArray = userTypeUtil.getUserData(userId);
            if(userDataArray == null || userDataArray.length == 0){
                logger.info("获取训练集时获取到一条错误用户数据");
                continue;
            }
            String[] trainArray = new String[userDataArray.length + 1];
            trainArray[0] = userDataArray[0];
            trainArray[1] = userDataArray[1];
            trainArray[2] = userDataArray[2];
            trainArray[3] = userDataArray[3];
            trainArray[4] = userDataArray[4];
            trainArray[5] = userDataArray[5];
            trainArray[6] = userDataArray[6];
            trainArray[7] = userDataArray[7];
            trainArray[8] = userDataArray[8];
            trainArray[9] = userDataArray[9];
            trainArray[10] = userDataArray[10];
            trainArray[11] = userDataArray[11];
            trainArray[12] = userDataArray[12];
            trainArray[13] = userDataArray[13];
            trainArray[14] = userDataArray[14];
            trainArray[15] = userAnswers.getUserType();
            trainArrays[i] = trainArray;
        }
        return trainArrays;
    }
}
