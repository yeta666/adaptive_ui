package com.adaptive.ui.aspect;

import com.adaptive.ui.domain1.User;
import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.service.UserService;
import com.adaptive.ui.type.MessageType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http请求控制类
 * Created by yeta on 2017/4/5/005.
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Autowired
    private UserService userService;

    //设置切点
    @Pointcut("execution(public * com.adaptive.ui.controller.*.*(..))")
    public void log(){

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        //在方法执行之前执行

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
        //加密后的用户密码验证
        User user = userService.findOne(Integer.valueOf(userId));
        if(!user.getUserPwd().equals(userPassword) ||
                user == null ||
                user.getUserPwd() == null ||
                user.getUserPwd().equals("")){
            logger.info("************************ 密码不正确，拦截非法访问！");
            throw new MyException(MessageType.message11);
        }
    }

    @After("log()")
    public void doAfter(){
        //在方法执行之后执行
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object){
        //在最后最后执行
        logger.info("response = {}", object.toString());
    }
}
