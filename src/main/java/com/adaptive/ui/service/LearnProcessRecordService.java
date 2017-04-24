package com.adaptive.ui.service;

import com.adaptive.ui.domain1.LearnProcessRecord;
import com.adaptive.ui.repositary1.LearnProcessRecordRepositary;
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
    private LearnProcessRecordRepositary learnProcessRecordRepositary;

    /**
     * 根据userId获取用户的学习数据
     * @param userId
     * @return
     */
    public List<LearnProcessRecord> findAllByBbreUserId(Integer userId){
        return learnProcessRecordRepositary.findAllByLpreUserId(userId);
    }
}
