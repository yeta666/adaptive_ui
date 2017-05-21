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
@RequestMapping(value = "/userType")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    /**
     * 处理获取用户类型的请求方法
     * @param userId
     * @return
     */
    @PostMapping(value = "/getUserType")
    public ResultUtil getUserType(
            @RequestParam(value = "userId", required = true) Integer userId,
            @RequestParam(value = "answers", required = false) String answers) throws IOException, ClassNotFoundException {
        return userTypeService.getUserType(userId, answers);
    }
}
