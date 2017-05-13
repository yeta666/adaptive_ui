package com.adaptive.ui.service;

import com.adaptive.ui.domain2.UserAnswers;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.repository1.UserRepository;
import com.adaptive.ui.repository2.UserAnswersRepository;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 和用户答案有关的逻辑操作类
 * Created by yeta on 2017/5/12/012.
 */
@Service
public class UserAnswersService {

    @Autowired
    private UserAnswersRepository userAnswersRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 通过用户id查询一条数据
     * @param userId
     * @return
     */
    public UserAnswers findOne(Integer userId){
        return userAnswersRepository.findOne(userId);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<UserAnswers> findAll(){
        return userAnswersRepository.findAll();
    }

    /**
     * 获取所有用户答案
     * @return
     */
    public ResultUtil getAll(){
        List<UserAnswers> userAnswersList = userAnswersRepository.findAll();
        if(userAnswersList == null || userAnswersList.size() == 0){
            throw new MyException(MessageType.message2);
        }
        List list = new ArrayList();
        for(int i = 0; i < userAnswersList.size(); i++){
            UserAnswers userAnswers = userAnswersList.get(i);
            Map map = new HashMap();
            map.put("userId", userAnswers.getUserId());
            map.put("userName", userRepository.findOne(userAnswers.getUserId()).getUserRealname());
            map.put("answers", userAnswers.getAnswers());
            map.put("userType", userAnswers.getUserType());
            list.add(map);
        }
        return new ResultUtil(true, MessageType.message1, list);
    }

    /**
     * 删除一条或多条用户答案的方法
     * @param userAnswers_ids
     * @return
     */
    public ResultUtil delete(String userAnswers_ids){
        //处理参数
        String[] userAnswers_id_array = userAnswers_ids.split(",");
        //遍历删除
        for(int i = 0; i < userAnswers_id_array.length; i++){
            Integer userAnswers_id = Integer.valueOf(userAnswers_id_array[i]);
            userAnswersRepository.delete(userAnswers_id);
            if(userAnswersRepository.findOne(userAnswers_id) != null){
                throw new MyException(MessageType.message14);
            }
        }
        return new ResultUtil(true, "", null);
    }

    /**
     * 保存一条数据的方法
     * @param userAnswers
     * @return
     */
    public UserAnswers save(UserAnswers userAnswers){
        return userAnswersRepository.save(userAnswers);
    }
}
