package com.adaptive.ui.service;

import com.adaptive.ui.domain2.Answers;
import com.adaptive.ui.domain2.Question;
import com.adaptive.ui.domain2.UserAnswers;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.repository2.AnswerRepository;
import com.adaptive.ui.repository2.UserAnswersRepository;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.repository2.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 与调查表题目有关的逻辑处理类
 * Created by yeta on 2017/4/7/007.
 */
@Service
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    /**
     * 获取调查表题目的方法
     * @return
     */
    public ResultUtil getAll() {
        List list = new ArrayList();
        //查询题目
        List<Question> questionList = questionRepository.findAll();
        for (int i = 0; i < questionList.size(); i++) {
            //封装返回数据
            Map map = new HashMap();
            map.put("id", questionList.get(i).getId());
            map.put("content", questionList.get(i).getContent());
            //查询答案
            Answers answers = answerRepository.findOne(questionList.get(i).getAnswersId());
            map.put("answers", answers);
            list.add(map);
        }
        if (questionList == null || questionList.size() == 0) {
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + " 从数据库获取所有所有题目失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }
        return new ResultUtil(true, "", list);
    }
}
