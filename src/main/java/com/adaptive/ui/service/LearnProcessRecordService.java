package com.adaptive.ui.service;

import com.adaptive.ui.domain1.Learnprocessrecord;
import com.adaptive.ui.repository1.LearnProcessRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与learnprocessrecord表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class LearnProcessRecordService {

    @Autowired
    private LearnProcessRecordRepository learnProcessRecordRepository;

    /**
     * 根据userId获取用户的学习数据
     * @param userId
     * @return
     */
    public List<Learnprocessrecord> findAllByBbreUserId(Integer userId){
        return learnProcessRecordRepository.findAllByLpreUserId(userId);
    }
}
