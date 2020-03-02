package com.springcloud.scribbon.aopproj.controller.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @dept 上海软件研发中心
 *  @description TODO
 *  @author HaoXin.Liu
 *  @date 2020/2/26 15:51
 **/
@RestController
@RequestMapping("testTo")
public class TestController {
    public static final Logger log = LoggerFactory.getLogger(TestController.class);

    /**
     * 增加测试Aop
     *
     * @dept 上海软件研发中心
     * @author HaoXin.Liu
     * @date 2020/2/26 15:55
     **/
    @PostMapping("del")
    public String addAop1() {
        int s = 1 / 0;
        log.info("del截取成功");
        return "success";
    }
}