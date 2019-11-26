package com.springcloud.sceurekaconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
/** 在注册中心的服务 */
@EnableDiscoveryClient
/** 表示这个方法需要调用远程服务 */
@EnableFeignClients
public class ScEurekaConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScEurekaConsumerApplication.class, args);
    }

}
