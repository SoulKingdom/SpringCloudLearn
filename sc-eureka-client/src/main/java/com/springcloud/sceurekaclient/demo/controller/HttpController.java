package com.springcloud.sceurekaclient.demo.controller;

import com.springcloud.sceurekaclient.demo.service.HttpService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description 测试服务提供者
 *  @author HaoXin.Liu
 *  @date 2019/11/25 11:26
 **/
@Api("规范框架demo")
@Validated
@RestController
@RequestMapping("demo")
public class HttpController {
    @Resource
    private static HttpService httpService;

    @ApiOperation(value = "Http调用Api的调用方法", notes = "调用外部api")
    @GetMapping(value = "/httpTest")
    public Boolean httpTest() {

        return httpService.callExternalApi();
    }
}