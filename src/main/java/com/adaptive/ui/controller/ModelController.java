package com.adaptive.ui.controller;

import com.adaptive.ui.service.ModelService;
import com.adaptive.ui.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 与模型有关的请求的控制器类
 * Created by yeta on 2017/5/13/013.
 */
@RestController
@RequestMapping(value = "/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    /**
     * 获取所有模型
     * @return
     */
    @GetMapping(value = "/getAll")
    public ResultUtil getAll() throws IOException, ClassNotFoundException {
        return modelService.getAll();
    }

    /**
     * 删除模型
     * @param model_ids
     * @return
     */
    @PostMapping(value = "/delete")
    public ResultUtil delete(@RequestParam(value = "ids", required = true) String model_ids){
        return modelService.delete(model_ids);
    }
}
