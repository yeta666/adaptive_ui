package com.adaptive.ui.util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by yeta on 2017/4/21/021.
 */
public class UserTypesUtilTest {

    @Autowired
    private UserTypeUtil userTypeUtil;

    @Test
    public void getUserData() throws Exception {
        String[] result = userTypeUtil.getUserData(532);

    }

}