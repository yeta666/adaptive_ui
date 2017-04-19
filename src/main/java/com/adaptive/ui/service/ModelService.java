package com.adaptive.ui.service;

import com.adaptive.ui.domain2.Model;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.repositary2.ModelRepositary;
import com.adaptive.ui.util.TreeModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 模型的逻辑处理类
 * Created by yeta on 2017/4/17/017.
 */
@Service
public class ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    @Autowired
    private ModelRepositary modelRepositary;

    @Autowired
    private TreeModelUtil treeModelUtil;

    /**
     * 增加一个模型的方法
     * @param model
     * @return
     */
    public Model save(Model model){
        return modelRepositary.save(model);
    }

    /**
     * 通过id查询一个模型的方法
     * @param id
     * @return
     */
    public Model findOne(Integer id){
        return modelRepositary.findOne(id);
    }

    /**
     * 查询全部模型的方法
     * @return
     */
    public List<Model> findAll(){
        return modelRepositary.findAll();
    }

    /**
     * 通过type查询一类模型的方法
     * @param type
     * @return
     */
    public List<Model> findByType(String type){
        return modelRepositary.findByType(type);
    }

    /**
     * 每天定时训练决策树模型的类
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Scheduled(cron = "0 0/1 8-20 * * ?")   //每天8:00 - 20:00一分钟执行一次
    //@Scheduled(cron = "0 6 * * *")  //每天6:00点执行一次
    //@Scheduled(cron = "0 0 16 * * ?")   //每天16:00执行一次
    public void trainModel() throws IOException, ClassNotFoundException {

        logger.info("********************开始训练模型！*********************");

        //获取训练集等数据
        treeModelUtil.getData();
        //训练决策树模型
        TreeNode rootNode = treeModelUtil.trainModel();
        //保存决策树模型
        treeModelUtil.saveModel(rootNode);

        logger.info("********************结束训练模型！*********************");
    }
}
