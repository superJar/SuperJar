package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.entity.Result;
import com.jar.pojo.OrderSetting;
import com.jar.service.OrderSettingService;
import com.jar.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.POST;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author:superJar
 * @date:2019/12/20
 * @time:13:36
 * @details:
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 将预约excel模板发送到后台接收
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        //解析excel, 返回一个字符串数组的集合
        try {
            List<String[]> dataList = POIUtils.readExcel(excelFile);
            //因为最后要转成OrderSetting对象返回到前端, 因此创建一个集合
            List<OrderSetting> list = new ArrayList<>();
            if (dataList != null || dataList.size() > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
                OrderSetting os = null;
                for (String[] rowDataStr : dataList) {
                    os = new OrderSetting(sdf.parse(rowDataStr[0]), Integer.valueOf(rowDataStr[1]));
                    //添加到List集合中
                    list.add(os);
                }

                orderSettingService.add(list);
            }

            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    /**
     * 在页面显示预约信息
     * @return
     */
    @PostMapping("/showAppInfo")
    public Result getInfo(@RequestParam String dateParam){

        List<Map<String,Integer>> list = orderSettingService.getAppInfo(dateParam);

        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);

    }

    @PostMapping("/resetNumber")
    public Result setNewReservations(@RequestBody OrderSetting orderSetting){

        orderSettingService.resetNumber(orderSetting);

        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}
