package com.adaptive.ui.repository2;

import com.adaptive.ui.domain2.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 与用户答案表有关的操作类
 * Created by yeta on 2017/4/25/025.
 */
public interface UserAnswersRepository extends JpaRepository<UserAnswers, Integer> {
}
