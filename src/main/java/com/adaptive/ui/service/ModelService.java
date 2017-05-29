package com.adaptive.ui.service;

import com.adaptive.ui.domain2.Model;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.id3Tree.TrainModel;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.repository2.ModelRepository;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.type.ModelType;
import com.adaptive.ui.util.ResultUtil;
import com.adaptive.ui.util.UserTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * 与模型的逻辑处理类
 * Created by yeta on 2017/4/17/017.
 */
@Service
public class ModelService {

    private static final Logger logger = LoggerFactory.getLogger(ModelService.class);

    private String modelContent = "";

    private String userType = "";

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private TrainArrayAttributesService trainArrayAttributesService;

    @Autowired
    private TrainArrayService trainArrayService;

    @Autowired
    private UserTypeUtil userTypeUtill;

    /**
     * 获取所有模型
     * @return
     */
    public ResultUtil getAll() throws IOException, ClassNotFoundException {
        List<Model> modelList = modelRepository.findAll();
        if(modelList == null || modelList.size() == 0){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + " 从数据库获取所有模型失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
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
        return new ResultUtil(true, "", list);
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
                //随机生成一个message码
                int num = new Random().nextInt(10000000);
                logger.info(num + " 从数据库获取要删除的模型失败！");
                throw new MyException(MessageType.message22 + " code:" + num);
            }
        }
        return new ResultUtil(true, "", null);
    }

    /**
     * 保存模型的方法
     * @param tree
     * @return
     */
    public void saveModel(TreeNode tree) throws IOException {
        Model model = new Model();
        //序列化决策树模型
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream= new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(tree);
        objectOutputStream.flush();
        objectOutputStream.close();
        model.setModel(byteArrayOutputStream.toByteArray());
        model.setType(ModelType.TYPE1);
        model.setCreateDate(new Date());
        Model thisModel = modelRepository.save(model);
        if(thisModel == null){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "保存模型失败！");
        }
    }

    /**
     * 通过模型类型查询最新的一个模型
     * @return
     */
    public TreeNode getModel() throws IOException, ClassNotFoundException {
        Model model = modelRepository.findFirstByTypeOrderByIdDesc(ModelType.TYPE1);
        if(model == null){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "从数据库中获取最新模型失败！");
            throw new MyException(MessageType.message11);
        }
        byte[] b = model.getModel();
        ByteArrayInputStream byteArrayInputStream= new ByteArrayInputStream(b);
        ObjectInputStream objectInputStream= new ObjectInputStream(byteArrayInputStream);
        return (TreeNode)objectInputStream.readObject();
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

        //获取训练集属性
        String[] attributesArray = trainArrayAttributesService.getTrainArrayAttributes(ModelType.TYPE1);

        //获取训练集
        Object[] trainArrays = trainArrayService.getTrainArrays();
        //Object[] trainArrays = trainArrayService.getTrainArrays2();

        //训练模型
        TrainModel trainModel = new TrainModel(attributesArray, trainArrays, ((String[])trainArrays[0]).length - 1);
        if(trainModel == null){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "初始化训练模型类失败！");
        }else{
            TreeNode tree = trainModel.train();
            if(tree == null){
                //随机生成一个message码
                int num = new Random().nextInt(10000000);
                logger.info(num + "训练模型失败！");
            }else{
                //保存模型
                saveModel(tree);
                //设置所有用户的用户类型
                userTypeUtill.setUserTypes(tree);
            }
        }
        logger.info("********************结束训练模型！*********************");
    }

    /**
     * 根据模型计算用户类型的具体方法
     * @param treeNode
     * @param data
     * @param attributesArray
     */
    public void getUserTypeByModel(TreeNode treeNode, String[] data, String[] attributesArray){
        //初始化以前保存的userType
        this.userType = "";
        //获取该节点对应“属性集”的索引
        int attribute_index = -1;
        for (int i = 0; i < attributesArray.length; i++) {
            if(treeNode.getNodeName() != null){
                if (treeNode.getNodeName().equals(attributesArray[i])) {
                    attribute_index = i;
                    break;
                }
            }
        }
        //获取该节点的子节点
        TreeNode[] childNodes = treeNode.getChildNodes();
        if(childNodes.length != 0){
            for(int j = 0; j < childNodes.length; j++){
                if(childNodes[j].getParentAttribute().equals(data[attribute_index])){
                    getUserTypeByModel(childNodes[j], data, attributesArray);
                }
            }
        }else{
            this.userType = treeNode.getNodeName();
        }
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

    /**
     * 因为
     * @return
     */
    public String getUserType() {
        return userType;
    }
}
