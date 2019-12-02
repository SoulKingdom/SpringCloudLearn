package com.springcloud.sczuul;

import com.springcloud.sczuul.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
