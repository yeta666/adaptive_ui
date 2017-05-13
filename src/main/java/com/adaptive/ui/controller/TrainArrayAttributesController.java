package com.adaptive.ui.controller;

import com.adaptive.ui.service.TrainArrayAttributesService;
import com.adaptive.ui.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 与训练集属性有关的请求控制类
 * Created by yeta on 2017/5/13/013.
 */
@RestController
@RequestMapping("/trainArrayAttributes")
public class TrainArrayAttributesController {

    @Autowired
    private TrainArrayAttributesService trainArrayAttributesService;

    /**
     * 获取所有训练集属性
     * @return
     */
    @GetMapping(value = "/getAll")
    public ResultUtil getAll(){
        return trainArrayAttributesService.getAll();
    }
}
