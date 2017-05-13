package com.adaptive.ui.service;

import com.adaptive.ui.domain2.Model;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.id3Tree.TrainModel;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.repository2.ModelRepository;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.util.TreeModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sun.misc.MessageUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 与模型的逻辑处理类
 * Created by yeta on 2017/4/17/017.
 */
@Service
public class ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    private String modelContent = "";

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private TreeModelUtil treeModelUtil;

    /**
     * 获取所有模型
     * @return
     */
    public ResultUtil getAll() throws IOException, ClassNotFoundException {
        List<Model> modelList = modelRepository.findAll();
        if(modelList == null || modelList.size() == 0){
            throw new MyException(MessageType.message2);
        }
        List list = new ArrayList();
        for(int i = 0; i < modelList.size(); i++){
            Model model = modelList.get(i);
            Map map = new HashMap();
            map.put("id", model.getId());
            map.put("createDate", model.getCreateDate());
            map.put("type", model.getType());
            byte[] b = model.getModel();
            ByteArrayInputStream byteArrayInputStream= new ByteArrayInputStream(b);
            ObjectInputStream objectInputStream= new ObjectInputStream(byteArrayInputStream);
            TreeNode rootNode = (TreeNode)objectInputStream.readObject();
            this.modelContent = "";
            this.printlnTree(rootNode);
            map.put("model", this.modelContent);
            list.add(map);
        }
        return new ResultUtil(true, MessageType.message1, list);
    }

    /**
     * 删除一个或多个模型
     * @param model_ids
     * @return
     */
    public ResultUtil delete(String model_ids){
        //处理参数
        String[] model_id_array = model_ids.split(",");
        //遍历删除
        for(int i = 0; i < model_id_array.length; i++){
            Integer model_id = Integer.valueOf(model_id_array[i]);
            modelRepository.delete(model_id);
            if(modelRepository.findOne(model_id) != null){
                throw new MyException(MessageType.message14);
            }
        }
        return new ResultUtil(true, "", null);
    }

    /**
     * 增加一个模型的方法
     * @param model
     * @return
     */
    public Model save(Model model){
        return modelRepository.save(model);
    }

    /**
     * 通过模型类型查询最新的一个模型
     * @param type
     * @return
     */
    public Model findFirstByTypeOrderByIdDesc(String type){
        return modelRepository.findFirstByTypeOrderByIdDesc(type);
    }

    /**
     * 每天定时训练决策树模型的类
     * @throws IOException
     * @throws ClassNotFoundException
     */
    //@Scheduled(cron = "0 0/1 8-20 * * ?")   //每天8:00 - 20:00一分钟执行一次
    @Scheduled(cron = "0 0 6 * * ?")   //每天6:00执行一次
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

    /**
     * 获取决策树模型内容的方法
     * @param rootNode
     */
    public void printlnTree(TreeNode rootNode){
        //System.out.println(rootNode.nodeName);
        this.modelContent += rootNode.nodeName;
        TreeNode[] childNodes = rootNode.childNodes;
        for(int i = 0; i < childNodes.length; i++){
            if(childNodes[i] != null){
                //System.out.println("如果：" + childNodes[i].parentAttribute);
                this.modelContent += "如果：" + childNodes[i].parentAttribute;
                printlnTree(childNodes[i]);
            }
        }
    }
}
