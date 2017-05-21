package com.adaptive.ui.controller;

import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 与调查表题目有关的请求的控制器类
 * Created by yeta on 2017/4/7/007.
 */
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 获取所有
     * @return
     */
    @GetMapping(value = "/getAll")
    public ResultUtil getAll(){
        return questionService.getAll();
    }

}
