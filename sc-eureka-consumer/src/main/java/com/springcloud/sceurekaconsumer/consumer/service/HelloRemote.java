package com.springcloud.sceurekaconsumer.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  @dept 上海软件研发中心
 *  @description Feign远程服务调用  FeignClient name:远程服务调用的名称
 *  @author HaoXin.Liu
 *  @date 2019/11/25 17:20
 **/
@FeignClient(name = "sc-eurka-client")
public interface HelloRemote {
    @RequestMapping(value = "/test/hello")
    String hello(@RequestParam(value = "name") String name);
}