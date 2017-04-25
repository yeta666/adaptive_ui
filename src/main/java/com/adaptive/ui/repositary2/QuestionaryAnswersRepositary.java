package com.adaptive.ui.repositary2;

import com.adaptive.ui.domain2.QuestionaryAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 与questionary_answers表有关的操作类
 * Created by yeta on 2017/4/25/025.
 */
public interface QuestionaryAnswersRepositary extends JpaRepository<QuestionaryAnswers, Integer> {
}
