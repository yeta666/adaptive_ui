package com.adaptive.ui.controller;

import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * 和用户类型有关的请求转发类
 * Created by yeta on 2017/4/6/006.
 */
@RestController
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    /**
     * 处理通过机器学习判断用户类型的请求
     * @param userId
     * @return
     */
    @GetMapping(value = "/getUserType")
    public ResultUtil getUserType(
            @RequestParam(value = "userId", required = true) Integer userId)
            throws IOException, ClassNotFoundException{
        return userTypeService.getUserType(userId);
    }

    /**
     * 处理通过调查表判断用户类型的请求
     * @param answers
     * @return
     */
    @PostMapping(value = "/getUserTypeByQuestionary")
    public ResultUtil getUserTypeByQuestionary(
            @RequestParam(value = "answers", required = true) String answers,
            @RequestParam(value = "userId", required = true) Integer userId){
        return userTypeService.getUserTypeByQuestionary(userId, answers);
    }
}
