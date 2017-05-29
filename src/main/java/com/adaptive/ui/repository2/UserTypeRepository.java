package com.adaptive.ui.repository2;

import com.adaptive.ui.domain2.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 与user_type表相关的操作类
 * Created by yeta on 2017/5/29/029.
 */
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
    
}
