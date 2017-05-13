package com.adaptive.ui.service;

import com.adaptive.ui.domain1.Studentmassedlearning;
import com.adaptive.ui.repository1.StudentMassedLearningRepository;
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
    private StudentMassedLearningRepository studentMassedLearningRepository;

    /**
     * 根据userId获取用户的集中学习数据
     * @param userId
     * @return
     */
    public List<Studentmassedlearning> findAllByBbreUserId(Integer userId){
        return studentMassedLearningRepository.findAllBySmleUserId(userId);
    }
}
