package com.adaptive.ui.service;

import com.adaptive.ui.domain2.TrainArrayAttribute;
import com.adaptive.ui.repositary2.TrainArrayAttributeRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * train_array_attribute表的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class TrainArrayAttributeService {

    @Autowired
    private TrainArrayAttributeRepositary trainArrayAttributeRepositary;

    /**
     * 根据modelType获取训练集属性的方法
     * @return
     */
    public TrainArrayAttribute findAllByModelType(String modelType){
        return trainArrayAttributeRepositary.findAllByModelType(modelType);
    }
}
