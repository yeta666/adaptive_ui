package com.adaptive.ui.service;

import com.adaptive.ui.domain1.ReAutotest;
import com.adaptive.ui.repository1.ReAutoTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与re_autotest表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class ReAutoTestService {

    @Autowired
    private ReAutoTestRepository reAutoTestRepository;

    /**
     * 根据userId获取用户的测试数据
     * @param userId
     * @return
     */
    public List<ReAutotest> findAllByBbreUserId(Integer userId){
        return reAutoTestRepository.findAllByRateUserId(userId);
    }
}
