package com.springcloud.sceurekaconsumer.consumer.controller;

import com.springcloud.sceurekaconsumer.consumer.service.HelloRemote;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description TODO
 *  @author HaoXin.Liu
 *  @date 2019/11/25 17:29
 **/
@RestController
@RequestMapping("test")
public class ConsumerController {
    @Resource
    private HelloRemote HelloRemote;

    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return HelloRemote.hello(name);
    }
}