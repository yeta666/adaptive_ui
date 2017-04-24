package com.adaptive.ui.repositary1;

import com.adaptive.ui.domain1.ReSelectCource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * re_selectcourse表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface ReSelectCourceRepositary extends JpaRepository<ReSelectCource, Integer> {

    /**
     * 根据userId获取用户的选课数据
     * @param userId
     * @return
     */
    public List<ReSelectCource> findAllByRscoUserId(Integer userId);
}
