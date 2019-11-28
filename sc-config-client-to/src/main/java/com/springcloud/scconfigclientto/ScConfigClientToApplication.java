package com.springcloud.scconfigclientto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ScConfigClientToApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScConfigClientToApplication.class, args);
    }

}
