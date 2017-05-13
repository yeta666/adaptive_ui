package com.adaptive.ui.aspect;

import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.type.MessageType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
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
@Order(2)
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

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

        //检查session中是否有用户信息
        /*if (request.getSession().getAttribute("userAuthenticationPassed?") == null || !(boolean) request.getSession().getAttribute("userAuthenticationPassed?")) {
            logger.info("**************************** 未经过认证，拦截非法访问！");
            throw new MyException(MessageType.message11);
        }*/
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
