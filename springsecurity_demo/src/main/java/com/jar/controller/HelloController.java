package com.jar.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:superJar
 * @date:2019/12/26
 * @time:15:14
 * @details:
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('add')")
    public void add(){
        System.out.println("add...");

    }

    @RequestMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(){
        System.out.println("delete");
    }

}
