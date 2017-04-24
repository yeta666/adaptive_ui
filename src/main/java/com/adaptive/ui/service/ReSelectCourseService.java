package com.adaptive.ui.service;

import com.adaptive.ui.domain1.ReSelectCourse;
import com.adaptive.ui.repositary1.ReSelectCourseRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 与re_selectcourse表有关的逻辑操作类
 * Created by yeta on 2017/4/20/020.
 */
@Service
public class ReSelectCourseService {

    @Autowired
    private ReSelectCourseRepositary reSelectCourseRepositary;

    /**
     * 根据userId获取用户的选课数据
     * @param userId
     * @return
     */
    public List<ReSelectCourse> findAllByRscoUserId(Integer userId){
        return reSelectCourseRepositary.findAllByRscoUserId(userId);
    }
}