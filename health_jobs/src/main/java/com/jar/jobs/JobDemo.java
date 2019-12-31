package com.jar.jobs;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author:superJar
 * @date:2019/12/19
 * @time:22:31
 * @details: Quartz 任务演示
 */

@Component
public class JobDemo {
    /**
     * 日期格式化器
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    /**
     * 任务触发时调用的方法
     */
    public void doJob(){
        System.out.println(sdf.format(new Date()));
    }
}
