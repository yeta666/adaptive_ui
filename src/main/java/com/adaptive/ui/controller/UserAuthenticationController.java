package com.adaptive.ui.controller;

import com.adaptive.ui.util.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户认证请求转发类
 * Created by yeta on 2017/5/5/005.
 */
@RestController
public class UserAuthenticationController {

    /**
     * 退出登录，清除用户标识
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public ResultUtil logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("userAuthenticationPassed?", false);
        if(session.getAttribute("userAuthenticationPassed?") != null && !(boolean)session.getAttribute("userAuthenticationPassed?")){
            return new ResultUtil(true, "退出登陆成功！", null);
        }else{
            return new ResultUtil(false, "退出登陆失败，请联系管理员！", null);
        }
    }
}
