package com.adaptive.ui.util;

import com.adaptive.ui.domain2.Model;
import com.adaptive.ui.domain2.TrainArray;
import com.adaptive.ui.id3Tree.TrainModel;
import com.adaptive.ui.id3Tree.TreeNode;
import com.adaptive.ui.service.ModelService;
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
    private TrainArrayService trainArrayService;

    /**
     * 获取训练集的方法
     * @return
     */
    public void getData(){

        //初始化属性集
        this.attributesArray = new String[]{"outlook", "temperature", "humidity", "windy", "play"};

        //从数据库中获取训练集
        List<TrainArray> trainArrayList = trainArrayService.findAll();

        //将数据转换成Object[String[], String[], ...]的形式
        Object[] trainArrays = new Object[trainArrayList.size()];
        for(int i = 0; i < trainArrayList.size(); i++){
            TrainArray trainArrayObject = trainArrayList.get(i);
            String[] trainArrayArray = new String[this.attributesArray.length];
            trainArrayArray[0] = trainArrayObject.getOutlook();
            trainArrayArray[1] = trainArrayObject.getTemperature();
            trainArrayArray[2] = trainArrayObject.getHumidity();
            trainArrayArray[3] = trainArrayObject.getWindy();
            trainArrayArray[4] = trainArrayObject.getPlay();
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

            //将源数据 + 计算结果存入训练集中
            TrainArray trainArray = new TrainArray();
            trainArray.setOutlook(data[0]);
            trainArray.setTemperature(data[1]);
            trainArray.setHumidity(data[2]);
            trainArray.setWindy(data[3]);
            trainArray.setPlay(this.modelResult);
            trainArrayService.save(trainArray);
        }
    }

    //给定数据，根据模型得出的结果的获取方法
    public String getModelResult() {
        return modelResult;
    }
}
