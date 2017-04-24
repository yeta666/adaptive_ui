package com.adaptive.ui.controller;

import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.util.TreeModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

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
    public String hello(){
        String hello = "";
        for(int i = 0; i < 9999; i++){
            hello += "hello, ";
        }
        return hello;
    }

}

