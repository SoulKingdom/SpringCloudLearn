package com.springcloud.sceurekaclient.demo.excel.controller;

import com.springcloud.sceurekaclient.demo.excel.entity.Device;
import com.springcloud.sceurekaclient.demo.excel.entity.User;
import com.util.util.poi.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  @dept 上海软件研发中心
 *  @description Excel操作
 *  @author HaoXin.Liu
 *  @date 2019/11/4 10:48
 **/
@RestController
@RequestMapping("/excel")
@Slf4j
public class ExcelController {
    /**
     * 创建excel表格 只存在一个sheet
     *
     * @dept 上海软件研发中心
     * @return 返回sheet名称
     * @author HaoXin.Liu
     * @date 2019/4/3 17:23
     **/
    @PostMapping("creatExcel")
    public String creatExcel(HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //创建Ecel模板
        String str = ExcelUtil.createExcelSheetTemplet(request,response, User.class);
        return str;
    }

    /**
     * 创建excel表格 存在多个sheet
     *
     * @dept 上海软件研发中心
     * @return 返回sheet名称
     * @author HaoXin.Liu
     * @date 2019/4/3 17:23
     **/
    @PostMapping("creatExcelSheets")
    public String creatExcelSheets(HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //创建Ecel模板
        List<Class> clses = new ArrayList<>(2);
        clses.add(User.class);
        clses.add(Device.class);
        String str = ExcelUtil.createExcelSheetsTemplet(request,response, clses);
        return str;
    }

    /**
     * * @function  读取excel文件保存
     *
     * @dept 上海软件研发中心
     * @param
     * @return 获取
     * @author HaoXin.Liu
     * @date 2019/4/4 10:43
     **/
    @PostMapping("readExcel")
    public Map<String, Object> readExcel(MultipartFile file) {
        Map<String, Object> mapTo = ExcelUtil.importExcelTo(file, Device.class);
        log.info(mapTo.toString());
        return mapTo;
    }

    /**
     * 响应信息获取
     *
     * @dept 上海软件研发中心
     * @return 获取
     * @author HaoXin.Liu
     * @date 2019/4/4 10:43
     **/
    @PostMapping("exportExcel")
    public String exportExcel(HttpServletRequest request,HttpServletResponse response) {
        List<Device> list = new ArrayList<>(2);
        list.add(new Device("1","一号设备","www.devieone.com"));
        list.add(new Device("1","一号设备","www.devieone.com"));
        String mapTo = ExcelUtil.exportExcelTo(request,response,"device",list);
        log.info("Excel数据导出成功=====>>文件名：[{}]｝",mapTo);
        return mapTo;
    }
}