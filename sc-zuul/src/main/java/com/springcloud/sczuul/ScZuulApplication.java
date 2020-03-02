package com.springcloud.sczuul;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@MapperScan("com.springcloud.sczuul.log.dao")
/**支持网关路由*/
@EnableZuulProxy
public class ScZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScZuulApplication.class, args);
    }


/*    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }*/
}
