package com.springcloud.sczuul.log.service;

import com.neusoft.gateway.log.entity.LogCond;
import com.springcloud.sczuul.log.entity.OperaLogVO;

/**
 *  @dept 上海软件研发中心
 *  @description 日志服务
 *  @author HaoXin.Liu
 *  @date 2020/2/29 14:07
 **/
public interface LogService {
    /**
     * 日志分页查询
     *
     * @dept 上海软件研发中心
     * @param  logCond 前端日志条件
     * @return 日志视图
     * @author HaoXin.Liu
     * @date 2020/2/29 14:39
     **/
    OperaLogVO listOperLog(LogCond logCond);
}