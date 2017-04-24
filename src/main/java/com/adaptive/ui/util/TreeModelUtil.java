package com.adaptive.ui.util;

import com.adaptive.ui.domain2.Model;
import com.adaptive.ui.domain2.TrainArray;
import com.adaptive.ui.domain2.TrainArrayAttribute;
import com.adaptive.ui.id3Tree.TrainModel;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.service.ModelService;
import com.adaptive.ui.service.TrainArrayAttributeService;
import com.adaptive.ui.service.TrainArrayService;
import com.adaptive.ui.type.ModelType;
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

    //属性集
    private String[] attributesArray;
    //训练集
    private Object[] trainArrays;
    //最后结果的索引
    int resultIndex;
    //给定数据，根据模型得出的结果
    private String modelResult;
    //最新的那个模型的id
    private Integer lastModelId;

    @Autowired
    private ModelService modelService;

    @Autowired
    private TrainArrayAttributeService trainArrayAttributeService;

    @Autowired
    private TrainArrayService trainArrayService;

    /**
     * 获取训练集的方法
     * @return
     */
    public void getData(){

        //初始化属性集
        TrainArrayAttribute trainArrayAttribute = trainArrayAttributeService.findAllByModelType(ModelType.TYPE1);
        String[] attributesArray = new String[17];
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
        attributesArray[16] = trainArrayAttribute.getModelType();
        this.attributesArray = attributesArray;

        //从数据库中获取训练集
        List<TrainArray> trainArraysList = trainArrayService.findAll();

        //将数据转换成Object[String[], String[], ...]的形式
        Object[] trainArrays = new Object[trainArraysList.size()];
        for(int i = 0; i < trainArraysList.size(); i++){
            TrainArray trainArrayObject = trainArraysList.get(i);
            String[] trainArrayArray = new String[this.attributesArray.length];
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
        }

        //初始化训练集
        this.trainArrays = trainArrays;

        //初始化最后结果的索引
        this.resultIndex = this.attributesArray.length - 1;
    }

    /**
     * 训练模型的方法
     * @return
     */
    public TreeNode trainModel() {
        //实例化训练模型类
        TrainModel trainModel = new TrainModel(this.attributesArray, this.trainArrays, this.resultIndex);
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
        model.setCreateData(new Date());
        Model thisModel = modelService.save(model);
        this.lastModelId = thisModel.getId();
        return thisModel;
    }

    /**
     * 根据id从数据库中获取决策树模型的方法
     * @return
     */
    public TreeNode getModel() throws IOException, ClassNotFoundException{
        Model model = modelService.findOne(this.lastModelId);
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

            //将源数据 + 计算结果存入训练集中，每个用户对应一条数据，如果数据库中已有该用户的数据，则更新

            TrainArray trainArray = new TrainArray();
            trainArray.setGender(data[0]);
            trainArray.setEntranceTime(data[1]);
            trainArray.setLoginNum(data[2]);
            trainArray.setBbsPostNum(data[3]);
            trainArray.setBbsPostTime(data[4]);
            trainArray.setBbsPostQuality(data[5]);
            trainArray.setBbsReplyNum(data[6]);
            trainArray.setBbsReplyTime(data[7]);
            trainArray.setLearnAllCourseNum(data[8]);
            trainArray.setLearnCourseBeginTime(data[9]);
            trainArray.setTestNum(data[10]);
            trainArray.setTestScore(data[11]);
            trainArray.setTestBeginTime(data[12]);
            trainArray.setMassedLearningNum(data[13]);
            trainArray.setChooseCourseNum(data[14]);
            trainArray.setChooseCoursePartsProportion(data[15]);
            trainArray.setUserType(this.modelResult);
            trainArrayService.save(trainArray);
        }
    }

    //给定数据，根据模型得出的结果的获取方法
    public String getModelResult() {
        return modelResult;
    }
    
}
