package com.adaptive.ui.repository1;

import com.adaptive.ui.domain1.Bbsreply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * bbsreply表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface BbsReplyRepository extends JpaRepository<Bbsreply, Integer> {

    /**
     * 根据userId获取用户的回复讨论数据
     * @param userId
     * @return
     */
    public List<Bbsreply> findAllByBbreUserId(Integer userId);

    public List<Bbsreply> findAllByBbreBbpoId(Integer bbsPostId);
}
