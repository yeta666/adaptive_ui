package com.adaptive.ui.controller;

import com.adaptive.ui.exception.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试类
 * Created by yeta on 2017/4/5/005.
 */
@RestController
public class HelloController {

    /**
     * 测试方法
     * @return
     */
    @GetMapping(value = "/hello")
    public void hello(@RequestParam(value = "id", required = false) Integer id){
        if(id == null || id.equals("")){
            throw new MyException("error");
        }
    }

}

