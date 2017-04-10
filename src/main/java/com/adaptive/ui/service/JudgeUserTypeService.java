package com.adaptive.ui.service;

import com.adaptive.ui.controller.JudgeUserTypeController;
import com.adaptive.ui.domain.Questionary;
import com.adaptive.ui.domain.Result;
import com.adaptive.ui.util.JudgeUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yeta on 2017/4/6/006.
 * 自适应界面的逻辑处理
 */
@Service
public class JudgeUserTypeService {

    private static final Logger logger = LoggerFactory.getLogger(JudgeUserTypeController.class);

    @Autowired
    private JudgeUserType judgeUserType;

    /**
     * 通过机器学习判断用户类型的方法
     */
    public Result judgeUserType(Integer userId){
        /**
         * 根据一些注册信息 + 学习信息 + 日志信息等来计算用户类型
         */
        return judgeUserType.judgeUserType(userId);
    }

    /**
     * 通过调查表判断用户类型的方法
     */
    public Result judgeUserTypeByQuestionary(String answers) {
        /**
         * 根据调查表内容 + 一些注册信息等来计算用户类型
         */
        return judgeUserType.judgeUserTypeByQuestionary(answers);
    }

}
