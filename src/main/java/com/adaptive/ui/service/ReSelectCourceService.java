package com.adaptive.ui.service;

import com.adaptive.ui.domain1.ReSelectcource;
import com.adaptive.ui.repository1.ReSelectCourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与re_selectcourse表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class ReSelectCourceService {

    @Autowired
    private ReSelectCourceRepository reSelectCourceRepository;

    /**
     * 根据userId获取用户的选课数据
     * @param userId
     * @return
     */
    public List<ReSelectcource> findAllByRscoUserId(Integer userId){
        return reSelectCourceRepository.findAllByRscoUserId(userId);
    }
}
