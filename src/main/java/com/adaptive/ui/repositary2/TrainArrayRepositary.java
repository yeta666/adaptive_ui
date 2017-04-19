package com.adaptive.ui.repositary2;

import com.adaptive.ui.domain2.TrainArray;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 与训练集表有关的操作
 * Created by yeta on 2017/4/19/019.
 */
public interface TrainArrayRepositary extends JpaRepository<TrainArray, Integer> {
}
