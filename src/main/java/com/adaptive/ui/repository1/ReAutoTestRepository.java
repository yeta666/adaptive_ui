package com.adaptive.ui.repository1;

import com.adaptive.ui.domain1.ReAutotest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * re_autotest表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface ReAutoTestRepository extends JpaRepository<ReAutotest, Integer> {

    /**
     * 根据userId获取用户的测试数据
     * @param userId
     * @return
     */
    public List<ReAutotest> findAllByRateUserId(Integer userId);
}
