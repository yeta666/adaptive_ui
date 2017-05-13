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

    /**
     * 保存一条
     * @param question
     * @param answer1
     * @param answer2
     * @return
     */
    @PostMapping(value = "/addOne")
    public ResultUtil addOne(@RequestParam(value = "question", required = true) String question,
                                     @RequestParam(value = "answer1", required = true) String answer1,
                                     @RequestParam(value = "answer2", required = true) String answer2){
        return questionService.addOne(question, answer1, answer2);
    }

    /**
     * 删除
     * @param question_ids
     * @return
     */
    @PostMapping(value = "/delete")
    public ResultUtil delete(@RequestParam(value = "question_ids", required = true) String question_ids){
        return questionService.delete(question_ids);
    }

}
