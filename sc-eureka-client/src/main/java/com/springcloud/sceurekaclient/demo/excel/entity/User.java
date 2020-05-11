package com.springcloud.sceurekaclient.demo.excel.entity;

import com.util.util.poi.annotation.ChineseName;
import com.util.util.poi.annotation.ColumnID;
import com.util.util.poi.annotation.ExcelFileName;
import lombok.Data;


/**
 *  @dept 上海软件研发中心
 *  @description Excel下载对应的格式
 *  @author HaoXin.Liu
 *  @date 2019/4/3 17:09
 **/
@ExcelFileName("用户管理")
@Data
public class User {

    @ColumnID
    @ChineseName("用户账号")
    private String account;
    @ChineseName("用户姓名")
    private String name;
    @ChineseName("用户头像路径")
    private String headPic;

}
