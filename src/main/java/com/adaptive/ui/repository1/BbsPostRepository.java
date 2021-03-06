package com.adaptive.ui.repository1;

import com.adaptive.ui.domain1.Bbspost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * bbspost表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface BbsPostRepository extends JpaRepository<Bbspost, Integer> {

    /**
     * 根据userId获取用户的发布讨论数据
     * @param userId
     * @return
     */
    public List<Bbspost> findAllByBbpoUserId(Integer userId);

    public List<Bbspost> findAllByBbpoCourId(Integer courseId);
}
