package com.adaptive.ui.repositary;

import com.adaptive.ui.domain.Questionary;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yeta on 2017/4/7/007.
 * Questionary表的操作
 */
public interface QuestionaryRepositary extends JpaRepository<Questionary, Integer> {
}
