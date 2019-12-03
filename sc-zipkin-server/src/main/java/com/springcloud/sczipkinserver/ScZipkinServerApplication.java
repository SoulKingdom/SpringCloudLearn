package com.springcloud.sczipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class ScZipkinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScZipkinServerApplication.class, args);
    }

}
