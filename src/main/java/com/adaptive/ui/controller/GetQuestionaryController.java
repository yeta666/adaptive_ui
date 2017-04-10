package com.adaptive.ui.controller;

import com.adaptive.ui.domain.Result;
import com.adaptive.ui.service.GetQuestionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yeta on 2017/4/7/007.
 * 获取调查表信息的控制器类
 */
@RestController
public class GetQuestionaryController {

    private static final Logger logger = LoggerFactory.getLogger(GetQuestionaryController.class);

    @Autowired
    private GetQuestionaryService getQuestionaryService;

    @GetMapping(value = "/getQuestionary")
    public Result getQuestionary(){
        return getQuestionaryService.getQuestionary();
    }
}
