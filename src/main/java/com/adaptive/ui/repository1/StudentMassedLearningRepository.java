package com.adaptive.ui.repository1;

import com.adaptive.ui.domain1.Studentmassedlearning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * studentmassedlearning表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface StudentMassedLearningRepository extends JpaRepository<Studentmassedlearning, Integer> {

    /**
     * 根据userId获取用户的集中学习数据
     * @param userId
     * @return
     */
    public List<Studentmassedlearning> findAllBySmleUserId(Integer userId);
}
