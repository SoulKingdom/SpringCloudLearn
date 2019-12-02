package com.springcloud.scclientto.produce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @dept 上海软件研发中心
 *  @description 测试服务提供者
 *  @author HaoXin.Liu
 *  @date 2019/11/25 11:26
 **/
@RestController
@RequestMapping("test")
public class HelloController {
    public static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String index(@RequestParam String name) {
        logger.info("hello " + name + "，this is second messge");
        //定时，为了zuul熔断器重试
        try {
            Thread.sleep(1000000);
        } catch (Exception e) {
            logger.error(" hello two error", e);
        }
        return "hello " + name + "，this is second messge";
    }
}