package com.adaptive.ui.controller;

import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.service.ModelService;
import com.adaptive.ui.service.TrainArrayAttributesService;
import com.adaptive.ui.service.TrainArrayService;
import com.adaptive.ui.service.UserTypeService;
import com.adaptive.ui.type.ModelType;
import com.adaptive.ui.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Date;

/**
 * 用于测试的类
 * Created by yeta on 2017/5/22/022.
 */
@RestController
public class TestController {

    @Autowired
    private TrainArrayAttributesService trainArrayAttributesService;

    @Autowired
    private TrainArrayService trainArrayService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private UserTypeService userTypeService;

    /*@GetMapping(value = "/testGetTrainArrayAttributes")
    public String[] testGetTrainArrayAttributes() {
        return trainArrayAttributesService.getTrainArrayAttributes(ModelType.TYPE1);
        //return null;
    }

    @GetMapping(value = "/testGetTrainArrays")
    public Object[] testGetTrainArrays(){
        return trainArrayService.getTrainArrays();
    }

    @GetMapping(value = "/testGetTrainArrays2")
    public Object[] testGetTrainArrays2(){
        return trainArrayService.getTrainArrays2();
    }

    @GetMapping(value = "/testTrainModel")
    public String testTrainModel() throws IOException, ClassNotFoundException {
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        modelService.trainModel();
        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println("训练模型用时：" + (end - begin) + " ms");
        return "";
    }

   @GetMapping(value = "/testGetModel")
    public String testGetModel() throws IOException, ClassNotFoundException {
        modelService.printlnTree(modelService.getModel());
        return "";
    }

    @GetMapping(value = "/testGetUserType")
    public ResultUtil testGetUserType(@RequestParam(value = "id") Integer id) throws IOException, ClassNotFoundException {
        return userTypeService.getUserType(id, null);
    }*/
}
