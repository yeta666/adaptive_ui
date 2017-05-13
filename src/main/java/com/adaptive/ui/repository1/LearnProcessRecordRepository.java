package com.adaptive.ui.repository1;

import com.adaptive.ui.domain1.Learnprocessrecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * learnprocessrecord表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface LearnProcessRecordRepository extends JpaRepository<Learnprocessrecord, Integer> {

    /**
     * 根据userId获取用户的学习数据
     * @param userId
     * @return
     */
    public List<Learnprocessrecord> findAllByLpreUserId(Integer userId);
}
