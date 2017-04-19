package com.adaptive.ui.repositary2;

import com.adaptive.ui.domain.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TreeModel表的操作类
 * Created by yeta on 2017/4/17/017.
 */
public interface ModelRepositary extends JpaRepository<Model, Integer> {
    List<Model> findByType(String type);
}
