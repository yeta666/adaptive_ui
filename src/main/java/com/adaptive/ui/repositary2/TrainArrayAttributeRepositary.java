package com.adaptive.ui.repositary2;

import com.adaptive.ui.domain2.TrainArrayAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * train_array_attribute表的操作
 * Created by yeta on 2017/4/20/020.
 */
public interface TrainArrayAttributeRepositary extends JpaRepository<TrainArrayAttribute, Integer> {

    /**
     * 根据modelType获取训练集属性的方法
     * @return
     */
    public TrainArrayAttribute findAllByModelType(String modelType);
}
