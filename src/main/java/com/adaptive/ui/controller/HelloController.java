package com.adaptive.ui.controller;

import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.util.TreeModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by yeta on 2017/4/5/005.
 * 测试类
 */
@RestController
public class HelloController {

    private String[] data1 = new String[]{"sunny", "hot", "high", "FALSE"};
    private String[] data2 = new String[]{"sunny", "hot", "high", "TRUE"};
    private String[] data3 = new String[]{"overcast", "hot", "high", "FALSE"};
    private String[] data4 = new String[]{"rainy", "mild", "high", "FALSE"};

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

    /**
     * 测试
     */
    @Autowired
    private TreeModelUtil treeModelUtil;

    @GetMapping(value = "/trainModel")
    public String trainModel() throws IOException, ClassNotFoundException {
        //获取训练集等数据
        treeModelUtil.getData();
        //训练决策树模型
        TreeNode rootNode = treeModelUtil.trainModel();
        //保存决策树模型
        treeModelUtil.saveModel(rootNode);
        //给一条数据，获取结果
        treeModelUtil.getResult(treeModelUtil.getModel(), data1);
        return treeModelUtil.getModelResult();
    }



}

