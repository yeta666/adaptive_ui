package com.adaptive.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yeta on 2017/4/5/005.
 * 测试类
 */
@RestController
public class HelloController {

    /**
     * 测试方法
     * @return
     */
    @GetMapping(value = "/hello")
    public String hello(){
        String hello = "";
        for(int i = 0; i < 9999; i++){
            hello += "hello, ";
        }
        return hello;
    }
}

