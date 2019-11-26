package com.springcloud.scclientto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ScClientToApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScClientToApplication.class, args);
    }

}
