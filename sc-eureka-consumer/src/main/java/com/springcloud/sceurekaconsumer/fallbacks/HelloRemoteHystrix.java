package com.springcloud.sceurekaconsumer.fallbacks;

import com.springcloud.sceurekaconsumer.consumer.service.HelloRemote;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  @dept 上海软件研发中心
 *  @description 熔断器fallBack回调
 *  @author HaoXin.Liu
 *  @date 2019/11/26 17:53
 **/
@Component
public class HelloRemoteHystrix implements HelloRemote {
    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello" + name + ", this messge send failed ";
    }
}