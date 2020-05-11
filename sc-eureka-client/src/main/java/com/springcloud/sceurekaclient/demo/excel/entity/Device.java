package com.springcloud.sceurekaclient.demo.excel.entity;

import com.util.util.poi.annotation.ChineseName;
import com.util.util.poi.annotation.ColumnID;
import com.util.util.poi.annotation.ExcelFileName;
import lombok.Data;

/**
 *  @dept 上海软件研发中心
 *  @description 设备信息
 *  @author HaoXin.Liu
 *  @date 2019/11/4 13:35
 **/
@ExcelFileName("设备管理")
@Data
public class Device {
    @ColumnID
    @ChineseName("设备Id")
    private String deviceId;
    @ChineseName("设备名称")
    private String deivecName;
    @ChineseName("设备图片")
    private String devicePic;

    public Device() {
      super();
    }

    public Device(String deviceId, String deivecName, String devicePic) {
        this.deviceId = deviceId;
        this.deivecName = deivecName;
        this.devicePic = devicePic;
    }
}