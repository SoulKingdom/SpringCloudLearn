package com.springcloud.scconfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ScConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScConfigClientApplication.class, args);
    }

}
