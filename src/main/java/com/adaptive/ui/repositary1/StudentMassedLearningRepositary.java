package com.adaptive.ui.repositary1;

import com.adaptive.ui.domain1.StudentMassedLearning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * studentmassedlearning表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface StudentMassedLearningRepositary extends JpaRepository<StudentMassedLearning, Integer> {

    /**
     * 根据userId获取用户的集中学习数据
     * @param userId
     * @return
     */
    public List<StudentMassedLearning> findAllBySmleUserId(Integer userId);
}
