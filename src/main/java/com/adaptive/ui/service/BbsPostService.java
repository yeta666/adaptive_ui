package com.adaptive.ui.service;

import com.adaptive.ui.domain1.Bbspost;
import com.adaptive.ui.repository1.BbsPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与bbspost表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class BbsPostService {

    @Autowired
    private BbsPostRepository bbsPostRepository;

    /**
     * 根据userId获取全部用户发布的讨论数据
     * @param userId
     * @return
     */
    public List<Bbspost> findAllByBbpoUserId(Integer userId){
        return bbsPostRepository.findAllByBbpoUserId(userId);
    }
}
