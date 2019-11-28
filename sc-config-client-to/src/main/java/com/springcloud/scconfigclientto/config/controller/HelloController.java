package com.springcloud.scconfigclientto.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试配置文件数据
 * @RefreshScope 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
 * c
 *
 *  @dept 上海软件研发中心
 *  @author HaoXin.Liu
 *  @date 2019/11/27 15:58
 **/
@RefreshScope
@RestController
public class HelloController {
    @Value("${neo.hello}")
    private String hello;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }
}