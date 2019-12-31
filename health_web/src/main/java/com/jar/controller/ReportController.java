package com.jar.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jar.constant.MessageConstant;
import com.jar.entity.Result;
import com.jar.pojo.PackageReport;
import com.jar.service.MemberService;
import com.jar.service.PackageService;
import com.jar.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.tools.attach.HotSpotVirtualMachine;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:superJar
 * @date:2019/12/27
 * @time:9:04
 * @details:
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private PackageService packageService;

    @Reference
    private ReportService reportService;

    @PostMapping("/getMemberReport")
    public Result getMemberReport(){

        Calendar calendar = Calendar.getInstance();
        //用Calendar将时间设置为12个月前的1号
        calendar.add(Calendar.MONTH,-12);
        calendar.set(Calendar.DATE,1);

        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //创建一个list, 接收前12个月里, 每个月的1号, 转成string类型
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,2);
            calendar.set(Calendar.DATE,1);
            calendar.add(Calendar.DATE,-1);

            list.add(sdf.format(calendar.getTime()));
        }

        //将list装入map里
        Map map = new HashMap();
        map.put("months",list);

        List<Integer> memberCount = memberService.findMemberCountByMonth(list);
        map.put("memberCount",memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    /**
     * 按会员性别分类, 并在前端以扇形图展示
     * @return
     */
    @PostMapping("/getMemberBySexReport")
    public Result getMemberBySexReport(){
        //定义一个返回到前端的容器
        Map map = new HashMap();
        //调用业务层方法, 获得对应数据
        List<Map<String,Object>> list1 = memberService.getMemberCountBySex();
        if(list1 == null){
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
        //放入到容器里
        map.put("memberCountBySex",list1);
        //创建另一个list
        List<String> list2 = new ArrayList<>();
        //将name单独拿出来, 放到map的另一个键值里
        for (Map<String, Object> map1 : list1) {
            String name = (String) map1.get("name");
            list2.add(name);
        }
        map.put("memberName",list2);
        return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    /**
     * 根据会员年龄段分类, 获取会员数量
     * @return
     */
    @PostMapping("/getMemberByAgeReport")
    public Result getMemberByAgeReport(){
        Map map = new HashMap();

        try {
            List<Map<String,Object>> list1 = memberService.getMemberCountByAge();

            map.put("memberCountByAge",list1);

            List<String> list2 = new ArrayList<>();
            for (Map<String, Object> map2 : list1) {
                String name = (String) map2.get("name");

                list2.add(name);
            }

            map.put("memberName",list2);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

        return new Result(true,MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);


    }

    /**
     * 获取套餐信息, 在前端以扇形图展示
     * @return
     */
    @PostMapping("/getPackageReport")
    public Result getPackageReport(){
        //前端需要我们后台传过去的格式为:
        /*"data":{
            "packageNames":["套餐1","套餐2","套餐3"],
            "packageCount":[
            {"name":"套餐1","value":10},
            {"name":"套餐2","value":30},
            {"name":"套餐3","value":25}
						   ]
        },*/
        List<Map<String,Object>> listForPackageCount = packageService.findPackageNameAndCount2();
        if(listForPackageCount == null){

            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
        Map map = new HashMap();

        map.put("packageCount",listForPackageCount);

        List<String> listForPackageName = new ArrayList<>();

        //对于packageName, 只要拿到packageCount里的Name即可
        for (Map<String, Object> map2 : listForPackageCount) {
            String name = (String) map2.get("name");
            listForPackageName.add(name);

        }
        map.put("packageName",listForPackageName);

        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);


//        Map map = new HashMap();
//        List<String> packageNameList = new ArrayList<>();
//        List<Integer> packageCountList = new ArrayList<>();
//        List<PackageReport> packageReports = packageService.findPackageNameAndCount();
//
//        if (packageReports == null) {
//            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
//        }
//
//        for (PackageReport packageReport : packageReports) {
//            packageNameList.add(packageReport.getPackageName());
//            packageCountList.add(packageReport.getPackageCount());
//        }
//
//        map.put("packageName",packageNameList);
//        map.put("packageCount",packageCountList);
//
//        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);

    }

    /**
     * 获取运营数据信息
     * @return
     */
    @PostMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        Map<String, Object>  map = null;
        try {
            //创建一个map用来装这些数据
            map = reportService.getBusinessReport();


        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
        return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
    }

    /**
     * 导出运营数据报告
     *      凡是用到POIUtils导出Excel, 都需要引入request 和 response形参
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){

        try {
            //远程调用报表服务获取报表
            Map<String, Object> report = reportService.getBusinessReport();

            //将报表里的所有数据都获取出来
            String reportDate = (String) report.get("reportDate");
            Integer todayNewMember = (Integer) report.get("todayNewMember");
            Integer totalMember = (Integer) report.get("totalMember");
            Integer thisWeekNewMember = (Integer) report.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) report.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) report.get("todayOrderNumber");
            Integer todayVisitsNumber = (Integer) report.get("todayVisitsNumber");
            Integer thisWeekOrderNumber = (Integer) report.get("thisWeekOrderNumber");
            Integer thisWeekVisitsNumber = (Integer) report.get("thisWeekVisitsNumber");
            Integer thisMonthOrderNumber = (Integer) report.get("thisMonthOrderNumber");
            Integer thisMonthVisitsNumbe = (Integer) report.get("thisMonthVisitsNumber");
            List<Map<String,Object>> hotPackages = (List<Map<String, Object>>) report.get("hotPackages");

            //获取excel文件的绝对路径
            String templateRealPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";

            //读取Excel文件, 获取Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(templateRealPath)));
            //获取对应的工作表
            XSSFSheet sheet = workbook.getSheetAt(0);
            //将值一个个植入到表格对象里
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumbe);

            int rowNum = 12;
            row = sheet.getRow(rowNum);
            for (Map<String, Object> hotPackage : hotPackages) {
                BigDecimal proportion = (BigDecimal) hotPackage.get("proportion");
                row.getCell(4).setCellValue((String) hotPackage.get("name"));
                row.getCell(5).setCellValue((Long) hotPackage.get("count"));
                row.getCell(6).setCellValue(proportion.doubleValue()); //占比 注意:用的是BigDecimal数据类型, 且需要doubleValue转换成double数据类型
                row.getCell(7).setCellValue((String) hotPackage.get("remark"));

                row = sheet.getRow(rowNum++);
            }

            //通过response对象的输出流对象进行文件下载
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Deposition","attachment;filename=report.xlsx");

            workbook.write(outputStream);

            outputStream.flush();
            outputStream.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }


    }
}
