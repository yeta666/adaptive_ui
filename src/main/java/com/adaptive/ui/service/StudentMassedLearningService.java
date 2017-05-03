package com.adaptive.ui.service;

import com.adaptive.ui.domain1.Studentmassedlearning;
import com.adaptive.ui.repositary1.StudentMassedLearningRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与studentmassedlearning表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class StudentMassedLearningService {

    @Autowired
    private StudentMassedLearningRepositary studentMassedLearningRepositary;

    /**
     * 根据userId获取用户的集中学习数据
     * @param userId
     * @return
     */
    public List<Studentmassedlearning> findAllByBbreUserId(Integer userId){
        return studentMassedLearningRepositary.findAllBySmleUserId(userId);
    }
}
