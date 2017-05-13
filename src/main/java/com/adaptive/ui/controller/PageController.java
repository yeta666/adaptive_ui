package com.adaptive.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面跳转控制类
 * Created by yeta on 2017/4/5/005.
 */
@Controller
public class PageController {

    @GetMapping(value = "/{page}")
    public String home(@PathVariable(value = "page") String page){
        return "html/" + page + ".html";
    }

}

