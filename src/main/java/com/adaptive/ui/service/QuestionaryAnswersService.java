package com.adaptive.ui.service;

import com.adaptive.ui.domain2.QuestionaryAnswers;
import com.adaptive.ui.repositary2.QuestionaryAnswersRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 调查表答案的逻辑处理类，主要用于保存答案和用户类型到数据库
 * Created by yeta on 2017/4/25/025.
 */
@Service
public class QuestionaryAnswersService {

    @Autowired
    private QuestionaryAnswersRepositary questionaryAnswersRepositary;

    /**
     * 保存调查表答案和根据答案计算出的用户类型的方法
     * @param questionaryAnswers
     * @return
     */
    public QuestionaryAnswers save(QuestionaryAnswers questionaryAnswers){
        return questionaryAnswersRepositary.save(questionaryAnswers);
    }

    /**
     * 获取所有调查表答案和用户类型的方法
     * @return
     */
    public List<QuestionaryAnswers> findAll(){
        return questionaryAnswersRepositary.findAll();
    }

    /**
     * 根据userId获取userType的方法
     * @param userId
     * @return
     */
    public QuestionaryAnswers findOne(Integer userId){
        return questionaryAnswersRepositary.findOne(userId);
    }
}
