package com.springcloud.scribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ScRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScRibbonApplication.class, args);
    }

    @Bean
    /**调用方法 开启负载均衡*/
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
