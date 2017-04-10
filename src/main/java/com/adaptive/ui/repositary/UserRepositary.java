package com.adaptive.ui.repositary;

import com.adaptive.ui.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yeta on 2017/4/6/006.
 * User表的操作
 */
public interface UserRepositary extends JpaRepository<User, Integer> {

}
