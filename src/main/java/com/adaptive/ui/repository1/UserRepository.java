package com.adaptive.ui.repository1;

import com.adaptive.ui.domain1.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * user表的操作类
 * Created by yeta on 2017/4/18/018.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}
