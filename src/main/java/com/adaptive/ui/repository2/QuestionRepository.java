package com.adaptive.ui.repository2;

import com.adaptive.ui.domain2.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Question表的操作类
 * Created by yeta on 2017/4/7/007.
 */
public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
