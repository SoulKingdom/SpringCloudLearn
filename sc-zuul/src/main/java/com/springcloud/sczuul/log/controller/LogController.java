package com.springcloud.sczuul.log.controller;

import com.neusoft.gateway.log.entity.LogCond;
import com.springcloud.core.exception.ScServerException;
import com.springcloud.core.restful.AppResponse;
import com.springcloud.sczuul.log.entity.OperaLogVO;
import com.springcloud.sczuul.log.service.LogService;
import io.swagger.annotations.Api;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description 操作日志管理
 *  @author HaoXin.Liu
 *  @date 2020/2/29 14:02
 **/
@Api("日志管理接口")
@RestController
@Validated
@RequestMapping("/log")
public class LogController {

    public static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @Resource
    private LogService logService;

    /**
     * 日志条件查询
     *
     * @dept 上海软件研发中心
     * @param logCond 日志查询条件
     * @return AppResponse 响应参数
     * @author HaoXin.Liu
     * @date 2020/2/29 15:05
     **/
        @RequestMapping(value = "/listLog", method = RequestMethod.POST)
    public AppResponse listLog(LogCond logCond, HttpServletRequest request) {
        try {
            OperaLogVO operaLogVo = logService.listOperLog(logCond);
            return AppResponse.success("日志查询成功", operaLogVo);
        } catch (Exception e) {
            logger.error("日志listSubDict失败", e);
            throw new ScServerException("日志查询失败，请重试");
        }
    }
}