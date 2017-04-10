package com.adaptive.ui.controller;

import com.adaptive.ui.domain.Result;
import com.adaptive.ui.service.JudgeUserTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by yeta on 2017/4/6/006.
 * 自适应界面请求的控制器
 */
@RestController
public class JudgeUserTypeController {

    private static final Logger logger = LoggerFactory.getLogger(JudgeUserTypeController.class);

    @Autowired
    private JudgeUserTypeService judgeUserTypeService;

    /**
     * 处理通过机器学习判断用户类型的请求
     * @param userId
     * @return
     */
    @GetMapping(value = "/getUserType")
    public Result getUserType(@RequestParam(value = "userId", required = true) Integer userId) {
        return judgeUserTypeService.judgeUserType(userId);
    }

    /**
     * 处理通过调查表判断用户类型的请求
     * @param answers
     * @return
     */
    @PostMapping(value = "/getUserTypeByQuestionary")
    public Result getUserTypeByQuestionary(@RequestParam(value = "answers") String answers){
        return judgeUserTypeService.judgeUserTypeByQuestionary(answers);
    }
}
