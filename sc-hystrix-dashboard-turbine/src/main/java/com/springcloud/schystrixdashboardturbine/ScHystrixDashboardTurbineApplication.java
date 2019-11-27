package com.springcloud.schystrixdashboardturbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableHystrixDashboard
//开启集群
@EnableTurbine
public class ScHystrixDashboardTurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScHystrixDashboardTurbineApplication.class, args);
    }

}
