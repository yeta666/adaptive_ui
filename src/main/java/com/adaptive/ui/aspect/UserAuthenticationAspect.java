package com.adaptive.ui.aspect;

import com.adaptive.ui.domain1.User;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.service.UserService;
import com.adaptive.ui.type.MessageType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户认证相关类
 * Created by yeta on 2017/5/5/005.
 */
@Aspect
@Component
@Order(1)
public class UserAuthenticationAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Autowired
    private UserService userService;

    //设置用户认证切点
    @Pointcut("execution(public * com.adaptive.ui.controller.UserTypeController.getUserType(..))")
    public void userAuthentication(){

    }

    @Before("userAuthentication()")
    public void doBefore(JoinPoint joinPoint){
        //获取request和response
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        //设置哪url可以跨域请求到本域
        response.setHeader("Access-Control-Allow-Origin", "*");

        //参数验证
        String userId = request.getParameter("userId");
        String userPassword = request.getParameter("userPassword");
        if(userId == null || userId.equals("") ||
                userPassword == null || userPassword.equals("")){
            logger.info("************************ 参数不足，拦截非法访问！");
            throw new MyException(MessageType.message11);
        }
        //获取用户信息
        User user = userService.findOne(Integer.valueOf(userId));
        if(user == null){
            logger.info("************************ 没有该用户，拦截非法访问！");
            throw new MyException(MessageType.message11);
        }
        //验证密码
        if(!user.getUserPwd().equals(userPassword) ||
                user == null ||
                user.getUserPwd() == null ||
                user.getUserPwd().equals("")){
            logger.info("************************ 密码不正确，拦截非法访问！");
            throw new MyException(MessageType.message11);
        }
        //保存用户信息到session
        request.getSession().setAttribute("userAuthenticationPassed?", true);
    }
}
