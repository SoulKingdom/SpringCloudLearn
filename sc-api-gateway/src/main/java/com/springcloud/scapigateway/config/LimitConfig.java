package com.springcloud.scapigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 *  @dept 上海软件研发中心
 *  @description 网关限流策略
 *  @author HaoXin.Liu
 *  @date 2019/12/4 11:27
 **/
@Component
public class LimitConfig {
    /**
      * 请求参数中的 user 字段来限流
      *
      * @dept 上海软件研发中心
      * @author HaoXin.Liu
      * @date 2019/12/4 11:28
      **/
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }
    /**
     * 根据请求 IP 地址来限流
     *
     * @dept 上海软件研发中心
     * @author HaoXin.Liu
     * @date 2019/12/4 11:28
     **/
  /*  @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }*/
}