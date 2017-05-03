package com.adaptive.ui.handle;

import com.adaptive.ui.exception.MyException;
import com.adaptive.ui.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义的异常处理类
 * Created by yeta on 2017/5/2/002.
 */
@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultUtil handle(Exception e){
        if(e instanceof MyException){
            MyException myException = (MyException) e;
            return new ResultUtil(false, myException.getMessage(), null);
        }else{
            logger.info("【系统异常】{}", e);
            return new ResultUtil(false, "未知错误！", null);
        }
    }
}
