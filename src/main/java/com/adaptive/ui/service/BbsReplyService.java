package com.adaptive.ui.service;

import com.adaptive.ui.domain1.BbsReply;
import com.adaptive.ui.repositary1.BbsReplyRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与bbsreply表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class BbsReplyService {

    @Autowired
    private BbsReplyRepositary bbsReplyRepositary;

    /**
     * 根据userId获取用户回复的讨论数据
     * @param userId
     * @return
     */
    public List<BbsReply> findAllByBbreUserId(Integer userId){
        return bbsReplyRepositary.findAllByBbreUserId(userId);
    }
}
