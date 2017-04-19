package com.adaptive.ui.repositary2;

import com.adaptive.ui.domain2.Questionary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Questionary表的操作类
 * Created by yeta on 2017/4/7/007.
 */
public interface QuestionaryRepositary extends JpaRepository<Questionary, Integer> {
}
