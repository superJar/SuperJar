package com.jar;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author:superJar
 * @date:2019/12/19
 * @time:22:49
 * @details:
 */
public class JobApplication {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:applicationContext-jobs.xml");
    }
}
