package com.springcloud.scclientto.produce.controller;

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
    @GetMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello " + name + "，this is second messge";
    }
}