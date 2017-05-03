package com.adaptive.ui.service;

import com.adaptive.ui.controller.QuestionaryController;
import com.adaptive.ui.domain2.Questionary;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.repositary2.QuestionaryRepositary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调查表有关的逻辑处理类
 * Created by yeta on 2017/4/7/007.
 */
@Service
public class QuestionaryService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionaryController.class);

    @Autowired
    private QuestionaryRepositary questionaryRepositary;

    public ResultUtil getQuestionary(){
        List<Questionary> questionaryList = questionaryRepositary.findAll();
        if(questionaryList == null || questionaryList.size() == 0){
            throw new MyException(MessageType.message5);
        }
        //封装返回数据
        List resultList = new ArrayList();
        for(int i = 0; i < questionaryList.size(); i++){
            Map resultMap = new HashMap();
            List answerList = new ArrayList();
            resultMap.put("id", questionaryList.get(i).getId());
            resultMap.put("question", questionaryList.get(i).getQuestion());
            answerList.add(questionaryList.get(i).getAnswer1());
            answerList.add(questionaryList.get(i).getAnswer2());
            resultMap.put("answer", answerList);
            resultList.add(resultMap);
        }
        return new ResultUtil(true, MessageType.message1, resultList);
    }
}
