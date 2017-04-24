package com.adaptive.ui.controller;

import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.service.QuestionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取调查表信息的控制器类
 * Created by yeta on 2017/4/7/007.
 */
@RestController
public class QuestionaryController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionaryController.class);

    @Autowired
    private QuestionaryService questionaryService;

    @GetMapping(value = "/getQuestionary")
    public ResultUtil getQuestionary(){
        return questionaryService.getQuestionary();
    }
}
