package com.jar.exception;

/**
 * @author:superJar
 * @date:2019/12/17
 * @time:12:08
 * @details:自定义异常, 将已经不符合业务逻辑操作的代码终止掉
 */
public class MyException extends RuntimeException {

    public MyException(String message){
        super(message);
    }
}
