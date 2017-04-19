package com.adaptive.ui.service;

import com.adaptive.ui.domain2.TrainArray;
import com.adaptive.ui.repositary2.TrainArrayRepositary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与训练集有关的逻辑处理类
 * Created by yeta on 2017/4/19/019.
 */
@Service
public class TrainArrayService {

    private static final Logger logger = LoggerFactory.getLogger(TrainArray.class);

    @Autowired
    private TrainArrayRepositary trainArrayRepositary;

    /**
     * 保存一条训练集数据的方法
     * @param trainArray
     * @return
     */
    public TrainArray save(TrainArray trainArray){
        return trainArrayRepositary.save(trainArray);
    }

    /**
     * 获取训练集全部数据的方法
     * @return
     */
    public List<TrainArray> findAll(){
        return trainArrayRepositary.findAll();
    }
}
