package com.adaptive.ui.repository2;

import com.adaptive.ui.domain2.TrainArrayAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * train_array_attribute表的操作
 * Created by yeta on 2017/4/20/020.
 */
public interface TrainArrayAttributesRepository extends JpaRepository<TrainArrayAttributes, Integer> {

    /**
     * 根据modelType获取训练集属性的方法
     * @return
     */
    TrainArrayAttributes findByModelType(String modelType);
}
