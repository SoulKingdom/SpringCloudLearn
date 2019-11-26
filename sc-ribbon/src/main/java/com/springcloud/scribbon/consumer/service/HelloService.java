package com.springcloud.scribbon.consumer.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description 通过远程服务调用
 *  @author HaoXin.Liu
 *  @date 2019/11/26 16:00
 **/
@Service
public class HelloService {

    @Resource
    RestTemplate restTemplate;

    public String hiService(String name) {
        return restTemplate.getForObject("http://sc-eurka-client/test/hello?name="+name,String.class);
    }


}
