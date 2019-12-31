package com.jar.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.entity.Result;
import com.jar.pojo.CheckItem;
import com.jar.service.CheckItemService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.List;


@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;


    /**
     * 新增检查项 (CheckItem)
     * @param checkItem
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result addItem(@RequestBody CheckItem checkItem){

        //调用业务层service的addItem方法
        checkItemService.addItem(checkItem);
        //没报错的话就返回result对象
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);

    }

    /**
     * 分页查询:
     * 提交的数据类型, 如果有实体类就用, 没有则用map
     * 工作中多条件时, 特别是有效范围查询都用map
     *
     * 该案例中, 已有封装好的实体类, 因此不用map
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务层查询出所有的checkItems,并用pageResult封装起来
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);

        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    /**
     * 查找出指定检查项
     * @param id
     * @return
     */
    @PostMapping("/findById")
    public Result findById(@RequestParam Integer id){
        CheckItem data = checkItemService.findById(id);
        return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,data);
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        boolean success = checkItemService.edit(checkItem);

        return new Result(success,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id){
        boolean success = checkItemService.deleteById(id);
        return new Result(success,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/findAll")
    public Result findAll(){
        List<CheckItem> checkItems = checkItemService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
    }


}
