package com.jar.entity;

import java.io.Serializable;

/**
 * 封装对象: 返回结果
 */
public class Result implements Serializable {
    private boolean flag; //执行结果, true:执行成功; false:执行失败
    private String message; //返回提示信息, 用于页面显示
    private Object data; //返回的数据

    public Result() {
    }

    public Result(boolean flag) {
        this.flag = flag;
    }

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag(){return flag;}

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
