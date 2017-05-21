package com.adaptive.ui.controller;

import com.adaptive.ui.service.UserAnswersService;
import com.adaptive.ui.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 与用户答案有关的请求的控制器类
 * Created by yeta on 2017/5/13/013.
 */
@RestController
@RequestMapping(value = "/userAnswers")
public class UserAnswersController {

    @Autowired
    private UserAnswersService userAnswersService;

    /**
     * 获取所有用户答案
     * @return
     */
    @GetMapping(value = "/getAll")
    public ResultUtil getAll(){
        return userAnswersService.getAll();
    }

    /**
     * 删除用户答案
     * @param userAnswers_ids
     * @return
     */
    @PostMapping(value = "/delete")
    public ResultUtil delete(@RequestParam(value = "ids", required = true) String userAnswers_ids){
        return userAnswersService.delete(userAnswers_ids);
    }
}
