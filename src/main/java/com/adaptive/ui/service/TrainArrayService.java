package com.adaptive.ui.service;

import com.adaptive.ui.domain2.TrainArray;
import com.adaptive.ui.repository2.TrainArrayRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(TrainArrayService.class);

    @Autowired
    private TrainArrayRepository trainArrayRepository;

    /**
     * 保存一条训练集数据的方法
     * @param trainArray
     * @return
     */
    public TrainArray save(TrainArray trainArray){
        return trainArrayRepository.save(trainArray);
    }

    /**
     * 获取训练集全部数据的方法
     * @return
     */
    public List<TrainArray> findAll(){
        return trainArrayRepository.findAll();
    }
}
