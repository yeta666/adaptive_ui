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
    public ResultUtil getAll(){
        List list = new ArrayList();
        //查询题目
        List<Question> questionList = questionRepository.findAll();
        for(int i = 0; i < questionList.size(); i++){
            //封装返回数据
            Map map = new HashMap();
            map.put("id", questionList.get(i).getId());
            map.put("content", questionList.get(i).getContent());
            //查询答案
            Answers answers = answerRepository.findOne(questionList.get(i).getAnswersId());
            map.put("answers", answers);
            list.add(map);
        }
        if(questionList == null || questionList.size() == 0){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + " 从数据库获取所有所有题目失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }
        return new ResultUtil(true, "", list);
    }

    /**
     * 增加一个调查表题目的方法
     * @param content
     * @param answerA
     * @param answerB
     * @return
     */
    public ResultUtil addOne(String content, String answerA, String answerB){
        Answers answers = new Answers();
        answers.setAnswer1(answerA);
        answers.setAnswer2(answerB);
        Answers answers1 = answerRepository.save(answers);
        Question question = new Question();
        question.setContent(content);
        question.setAnswersId(answers1.getId());
        Question question1 = questionRepository.save(question);
        if(question1 == null || question1.getContent() == null || question1.getAnswersId() == null){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "题目未能添加到数据库！");
            throw new MyException(MessageType.message33 + " code:" + num);
        }
        return new ResultUtil(true, "", null);
    }

    /**
     * 删除题目的方法
     * @param question_ids
     * @return
     */
    public ResultUtil delete(String question_ids){
        //处理参数
        String[] question_id_array = question_ids.split(",");
        //遍历删除
        for(int i = 0; i < question_id_array.length; i++){
            Integer question_id = Integer.valueOf(question_id_array[i]);
            Question question = questionRepository.findOne(question_id);
            answerRepository.delete(question.getAnswersId());
            questionRepository.delete(question_id);
            if(answerRepository.findOne(question.getAnswersId()) != null || questionRepository.findOne(question_id) != null){
                //随机生成一个message码
                int num = new Random().nextInt(10000000);
                logger.info(num + "删除题目失败！");
                throw new MyException(MessageType.message22 + " code:" + num);
            }
        }
        return new ResultUtil(true, "", null);
    }
}
