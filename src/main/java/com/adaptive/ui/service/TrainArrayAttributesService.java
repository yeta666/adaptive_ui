package com.adaptive.ui.service;

import com.adaptive.ui.domain2.TrainArrayAttributes;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.repository2.TrainArrayAttributesRepository;
import com.adaptive.ui.type.MessageType;
import com.adaptive.ui.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 与训练集属性又挂你的的逻辑处理类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class TrainArrayAttributesService {

    private static final Logger logger = LoggerFactory.getLogger(TrainArrayAttributesService.class);

    @Autowired
    private TrainArrayAttributesRepository trainArrayAttributesRepository;

    /**
     * 根据modelType获取训练集属性的方法
     * @return
     */
    public TrainArrayAttributes findByModelType(String modelType){
        return trainArrayAttributesRepository.findByModelType(modelType);
    }

    /**
     * 获取所有训练集属性
     * @return
     */
    public ResultUtil getAll(){
        List<TrainArrayAttributes> trainArrayAttributesList = trainArrayAttributesRepository.findAll();
        if(trainArrayAttributesList == null || trainArrayAttributesList.size() == 0){
            //随机生成一个message码
            int num = new Random().nextInt(10000000);
            logger.info(num + "获取所有训练集属性失败！");
            throw new MyException(MessageType.message11 + " code:" + num);
        }
        return new ResultUtil(true, "", trainArrayAttributesList);
    }

    /**
     * 注：
     * 因为目前训练集和训练集的获取方法是写死的，所以不能提供增加、删除训练集属性的方法，训练集和训练集属性要求高度统一！！！
     */

    /**
     * 保存一个训练集属性
     * @param id
     * @param attribute
     * @return
     */
    /*public ResultUtil addOne(String id, String attribute){
        //从数据库中查找
        TrainArrayAttributes trainArrayAttributes = trainArrayAttributesRepository.findOne(Integer.valueOf(id));
        if(trainArrayAttributes == null){
            throw new MyException(MessageType.message13);
        }
        //获取属性
        String attributes = trainArrayAttributes.getAttributes();
        //往属性里添加
        attributes = attribute + "," + attribute;
        //设置
        trainArrayAttributes.setAttributes(attributes);
        //保存
        trainArrayAttributesRepository.save(trainArrayAttributes);
        //验证
        if(!trainArrayAttributesRepository.findOne(Integer.valueOf(id)).getAttributes().equals(attributes)){
            throw new MyException(MessageType.message13);
        }
        return new ResultUtil(true, "", null);
    }*/

    /**
     * 删除一个或多个训练集属性
     * @param id
     * @param attributes_indexs
     * @return
     */
    /*public ResultUtil delete(String id, String attributes_indexs){
        //处理参数
        String[] attributes_indexs_array = attributes_indexs.split(",");
        //获取
        TrainArrayAttributes trainArrayAttributes = trainArrayAttributesRepository.findOne(id);
        if(trainArrayAttributes == null){
            throw new MyException(MessageType.message14);
        }
        //获取属性，处理成字符串数组
        String[] attributes_array = trainArrayAttributes.getAttributes().split(",");
        //根据属性序号删除
        for(int i = 0; i < attributes_indexs_array.length; i++){
            int index = Integer.valueOf(attributes_indexs_array[i]);

        }
        return new ResultUtil(true, "", null);
    }*/
}
