package com.jar.controller;

import com.jar.constant.MessageConstant;
import com.jar.entity.Result;
import com.jar.exception.MyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @author:superJar
 * @date:2019/12/17
 * @time:12:24
 * @details:
 */
//该注解可以捕获controller抛出的异常
@RestControllerAdvice
public class MyExceptionHandler {

    //@exceptionHandler来捕获异常

    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException my){
        my.printStackTrace();
        return new Result(false,my.getMessage());
    }

    //还可以捕获一个最大异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return new Result(false,"T_T 不知道出啥问题了");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result handleMyAccessException(AccessDeniedException my){
        my.printStackTrace();
        return new Result(false,"你没有这个权限访问, 请联系管理员");
    }
}
