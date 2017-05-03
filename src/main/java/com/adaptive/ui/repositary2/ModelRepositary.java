package com.adaptive.ui.repositary2;

import com.adaptive.ui.domain2.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TreeModel表的操作类
 * Created by yeta on 2017/4/17/017.
 */
public interface ModelRepositary extends JpaRepository<Model, Integer> {

    /**
     * 根据模型类型获取全部模型
     * @param type
     * @return
     */
    Model findFirstByTypeOrderByIdDesc(String type);
}
