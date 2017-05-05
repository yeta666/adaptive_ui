package com.adaptive.ui.util;

import com.adaptive.ui.domain2.Model;
import com.adaptive.ui.domain2.QuestionaryAnswers;
import com.adaptive.ui.domain2.TrainArrayAttribute;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.id3Tree.TrainModel;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.service.ModelService;
import com.adaptive.ui.service.QuestionaryService;
import com.adaptive.ui.service.TrainArrayAttributeService;
import com.adaptive.ui.service.TrainArrayService;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.type.ModelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * 与决策树模型有关的工具类
 * Created by yeta on 2017/4/16/016.
 */
@Component
public class TreeModelUtil {

    private final static Logger logger = LoggerFactory.getLogger(TreeModelUtil.class);

    //属性集
    private String[] attributesArray;
    //训练集
    private Object[] trainArrays;
    //最后结果的索引
    private int resultIndex;
    //给定数据，根据模型得出的结果
    private String modelResult;

    @Autowired
    private ModelService modelService;

    @Autowired
    private TrainArrayAttributeService trainArrayAttributeService;

    @Autowired
    private TrainArrayService trainArrayService;

    @Autowired
    private UserTypeUtil userTypeUtil;

    @Autowired
    private QuestionaryService questionaryService;

    /**
     * 获取训练集的方法
     * @return
     */
    public void getData(){

        //初始化属性集
        TrainArrayAttribute trainArrayAttribute = trainArrayAttributeService.findByModelType(ModelType.TYPE1);
        if(trainArrayAttribute == null){
            logger.info("**************************** " + MessageType.message7);
            throw new MyException(MessageType.message7);
        }
        String[] attributesArray = new String[16];
        attributesArray[0] = trainArrayAttribute.getAttribute1();
        attributesArray[1] = trainArrayAttribute.getAttribute2();
        attributesArray[2] = trainArrayAttribute.getAttribute3();
        attributesArray[3] = trainArrayAttribute.getAttribute4();
        attributesArray[4] = trainArrayAttribute.getAttribute5();
        attributesArray[5] = trainArrayAttribute.getAttribute6();
        attributesArray[6] = trainArrayAttribute.getAttribute7();
        attributesArray[7] = trainArrayAttribute.getAttribute8();
        attributesArray[8] = trainArrayAttribute.getAttribute9();
        attributesArray[9] = trainArrayAttribute.getAttribute10();
        attributesArray[10] = trainArrayAttribute.getAttribute11();
        attributesArray[11] = trainArrayAttribute.getAttribute12();
        attributesArray[12] = trainArrayAttribute.getAttribute13();
        attributesArray[13] = trainArrayAttribute.getAttribute14();
        attributesArray[14] = trainArrayAttribute.getAttribute15();
        attributesArray[15] = trainArrayAttribute.getAttribute16();
        this.attributesArray = attributesArray;


        /*//从数据库中获取训练集
        List<TrainArray> trainArraysList = trainArrayService.findAll();

        //将数据转换成Object[String[], String[], ...]的形式
        Object[] trainArrays = new Object[trainArraysList.size()];
        for(int i = 0; i < trainArraysList.size(); i++){
            TrainArray trainArrayObject = trainArraysList.get(i);
            String[] trainArrayArray = new String[this.attributesArray.length + 1];
            trainArrayArray[0] = trainArrayObject.getGender();
            trainArrayArray[1] = trainArrayObject.getEntranceTime();
            trainArrayArray[2] = trainArrayObject.getLoginNum();
            trainArrayArray[3] = trainArrayObject.getBbsPostNum();
            trainArrayArray[4] = trainArrayObject.getBbsPostTime();
            trainArrayArray[5] = trainArrayObject.getBbsPostQuality();
            trainArrayArray[6] = trainArrayObject.getBbsReplyNum();
            trainArrayArray[7] = trainArrayObject.getBbsReplyTime();
            trainArrayArray[8] = trainArrayObject.getLearnAllCourseNum();
            trainArrayArray[9] = trainArrayObject.getLearnCourseBeginTime();
            trainArrayArray[10] = trainArrayObject.getTestNum();
            trainArrayArray[11] = trainArrayObject.getTestScore();
            trainArrayArray[12] = trainArrayObject.getTestBeginTime();
            trainArrayArray[13] = trainArrayObject.getMassedLearningNum();
            trainArrayArray[14] = trainArrayObject.getChooseCourseNum();
            trainArrayArray[15] = trainArrayObject.getChooseCoursePartsProportion();
            trainArrayArray[16] = trainArrayObject.getUserType();
            trainArrays[i] = trainArrayArray;
        }*/

        //从数据库中获取训练集
        List<QuestionaryAnswers> questionaryAnswersList = questionaryService.findAll();
        if(questionaryAnswersList == null || questionaryAnswersList.size() == 0){
            logger.info("**************************** " + MessageType.message8);
            throw new MyException(MessageType.message8);
        }
        Object[] trainArrays = new Object[questionaryAnswersList.size()];
        for(int i = 0; i < questionaryAnswersList.size(); i++){
            QuestionaryAnswers questionaryAnswers = questionaryAnswersList.get(i);
            //获取一个userId
            Integer userId = questionaryAnswers.getUserId();
            if(userId == null || userId.equals("")){
                logger.info("**************************** " + MessageType.message8);
                throw new MyException(MessageType.message8);
            }
            //根据userId获取训练集数据
            String[] userDataArray = userTypeUtil.getUserData(userId);
            if(userDataArray == null || userDataArray.length == 0){
                logger.info("**************************** " + MessageType.message3);
                throw new MyException(MessageType.message3);
            }
            String[] trainArray = new String[userDataArray.length + 1];
            trainArray[0] = userDataArray[0];
            trainArray[1] = userDataArray[1];
            trainArray[2] = userDataArray[2];
            trainArray[3] = userDataArray[3];
            trainArray[4] = userDataArray[4];
            trainArray[5] = userDataArray[5];
            trainArray[6] = userDataArray[6];
            trainArray[7] = userDataArray[7];
            trainArray[8] = userDataArray[8];
            trainArray[9] = userDataArray[9];
            trainArray[10] = userDataArray[10];
            trainArray[11] = userDataArray[11];
            trainArray[12] = userDataArray[12];
            trainArray[13] = userDataArray[13];
            trainArray[14] = userDataArray[14];
            trainArray[15] = userDataArray[15];
            trainArray[16] = questionaryAnswers.getUserType();
            trainArrays[i] = trainArray;
        }

        //初始化训练集
        this.trainArrays = trainArrays;

        //初始化最后结果的索引
        this.resultIndex = ((String[])this.trainArrays[0]).length - 1;
    }

    /**
     * 训练模型的方法
     * @return
     */
    public TreeNode trainModel() {
        //实例化训练模型类
        TrainModel trainModel = new TrainModel(this.attributesArray, this.trainArrays, this.resultIndex);
        if(trainModel == null){
            logger.info("**************************** " + MessageType.message9);
            throw new MyException(MessageType.message9);
        }
        //返回决策树模型
        return trainModel.train();
    }

    /**
     * 把决策树模型保存到数据库中的方法
     * @param treeNode
     */
    public Model saveModel(TreeNode treeNode) throws IOException, ClassNotFoundException{
        Model model = new Model();
        //序列化决策树模型
        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream= new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(treeNode);
        objectOutputStream.flush();
        objectOutputStream.close();
        model.setModel(byteArrayOutputStream.toByteArray());
        model.setType(ModelType.TYPE1);
        model.setCreateDate(new Date());
        Model thisModel = modelService.save(model);
        if(thisModel == null){
            logger.info("**************************** " + MessageType.message10);
            throw new MyException(MessageType.message10);
        }
        return thisModel;
    }

    /**
     * 数据库中获取决策树模型的方法
     * 取决策树模型类型的最后一条数据
     * @return
     */
    public TreeNode getModel() throws IOException, ClassNotFoundException{
        Model model = modelService.findFirstByTypeOrderByIdDesc(ModelType.TYPE1);
        if(model == null){
            logger.info("**************************** " + MessageType.message6);
            throw new MyException(MessageType.message6);
        }
        byte[] b = model.getModel();
        ByteArrayInputStream byteArrayInputStream= new ByteArrayInputStream(b);
        ObjectInputStream objectInputStream= new ObjectInputStream(byteArrayInputStream);
        return (TreeNode)objectInputStream.readObject();
    }

    /**
     * 给定一条数据，走一遍决策树模型，得出最后的结果的方法
     * @param treeNode 决策树模型
     * @param data 给定的一条数据
     */
    public void getResult(TreeNode treeNode, String[] data){
        //获取该节点对应“属性集”的索引
        int attribute_index = -1;
        for(int i = 0; i < this.attributesArray.length; i++){
            if(treeNode.getNodeName().equals(this.attributesArray[i])){
                attribute_index = i;
                break;
            }
        }
        //获取该节点的子节点
        TreeNode[] childNodes = treeNode.getChildNodes();
        if(childNodes.length != 0){
            for(int j = 0; j < childNodes.length; j++){
                if(childNodes[j].getParentAttribute().equals(data[attribute_index])){
                    getResult(childNodes[j], data);
                }
            }
        }else{
            this.modelResult = treeNode.getNodeName();
        }
    }

    /**
     * 给定数据，根据模型得出的结果的获取方法
     */
    public String getModelResult() {
        return modelResult;
    }

    /**
     * 设置模型结果的方法
     */
    public void setModelResult(){
        this.modelResult = "";
    }
    
}
