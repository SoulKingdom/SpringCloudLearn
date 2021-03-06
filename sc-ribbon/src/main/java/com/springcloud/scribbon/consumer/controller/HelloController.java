package com.springcloud.scribbon.consumer.controller;

import com.springcloud.scribbon.consumer.service.HelloService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description ribbon测试控制层
 *  @author HaoXin.Liu
 *  @date 2019/11/26 16:08
 **/
@RestController
@RequestMapping("ribbon")
public class HelloController {
    @Resource
    private HelloService helloService;

    @GetMapping(value = "/hello")
    public String hi(@RequestParam String name) {
        return helloService.hiService(name);
    }

    @GetMapping(value = "/foo")
    public String foo(@RequestParam(value = "foo", required = false) String foo) {
        return "hello " + foo + "!";
    }
}