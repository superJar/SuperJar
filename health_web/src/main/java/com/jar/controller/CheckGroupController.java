package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.entity.PageResult;
import com.jar.entity.QueryPageBean;
import com.jar.entity.Result;
import com.jar.pojo.CheckGroup;
import com.jar.pojo.CheckItem;
import com.jar.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.List;

/**
 * @author:superJar
 * @date:2019/12/17
 * @time:22:32
 * @details:
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 增加检查组
     * @param checkGroup
     * @param checkItemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){

        checkGroupService.add(checkGroup,checkItemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务层方法, 返回对象PageResult, 由于需要显示CheckGroup的信息, 泛型为CheckGroup
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);

        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);

    }

    /**
     * 根据id查找出检查组
     * @param id
     * @return
     */
    @PostMapping("/findById")
    public Result findById(@RequestParam Integer id){
        //调用业务层根据id查找出检查组
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }

    /**
     * 根据t_checkgroup_checkitem表格找出checkItem的id
     * @param id
     * @return
     */
    @PostMapping("/getCheckItemIds")
    public Result getCheckItemIds(@RequestParam Integer id){
        //调用业务层, 并根据t_checkgroup_checkitem表格找出checkItem的id
        List<Integer> list = checkGroupService.getCheckItemIds(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
    }

    /**
     * 修改checkGroup
     * @param checkGroup
     * @return
     */
    @PostMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup){
        //调用业务层
        boolean success = checkGroupService.update(checkGroup);
        return new Result(success,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 删除检查组
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id){
        boolean success = checkGroupService.delete(id);
        return new Result(success,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询所有检查组
     * @return
     */
    @PostMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> list = checkGroupService.findAll();

        if (list != null && list.size() > 0) {
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}
