package com.adaptive.ui.repository2;

import com.adaptive.ui.domain2.Model;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * model表的操作类
 * Created by yeta on 2017/4/17/017.
 */
public interface ModelRepository extends JpaRepository<Model, Integer> {

    /**
     * 根据模型类型获取模型，然后按照降序排序，获取第一个
     * @param type
     * @return
     */
    Model findFirstByTypeOrderByIdDesc(String type);
}
