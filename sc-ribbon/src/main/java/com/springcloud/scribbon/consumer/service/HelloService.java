package com.springcloud.scribbon.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
    @HystrixCommand(fallbackMethod = "helloErro")
    public String hiService(String name) {
        return restTemplate.getForObject("http://sc-eurka-client/test/hello?name="+name,String.class);
    }

    /**
      * 熔断器fallback数据
      *
      * @dept 上海软件研发中心
      * @param name 对应参数
      * @return 熔断器fallback数据
      * @author HaoXin.Liu
      * @date 2019/11/26 18:11
      **/
    private String helloErro(String name){
        return "hi,"+name+",sorry,error!";
    }


}
