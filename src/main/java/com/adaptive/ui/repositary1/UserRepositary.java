package com.adaptive.ui.repositary1;

import com.adaptive.ui.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface UserRepositary extends JpaRepository<User, Integer> {
}
