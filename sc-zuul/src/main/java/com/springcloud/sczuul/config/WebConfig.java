package com.springcloud.sczuul.config;

import com.springcloud.core.page.PageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *  @dept 上海软件研发中心
 *  @description 配置文件注册
 *  @author HaoXin.Liu
 *  @date 2020/3/24 16:38
 **/
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    /**
     * 添加拦截器
     * @param registry 拦截器信息注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        //添加页面拦截器
        registry.addInterceptor(new PageInterceptor());
        System.out.println("===========   拦截器注册完毕   ===========");
    }
}