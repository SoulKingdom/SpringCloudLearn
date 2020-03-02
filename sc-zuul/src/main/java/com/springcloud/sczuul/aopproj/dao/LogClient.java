package com.springcloud.sczuul.aopproj.dao;

import com.springcloud.sczuul.aopproj.entity.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface LogClient {

    /**
     * 部门：通信与企业互联事业部
     * 功能：日志写入数据库
     * 描述：略
     * 作成者：leihao
     * 作成时间：2018-5-19
     */
    void logsToDb(Log log, Exception ex);

    /**
     * 部门：通信与企业互联事业部
     * 功能：日志查询
     * 描述：
     * 作成者：leihao
     * 作成时间：2018-5-19
     */
    Grid<Map<String, String>> findLogList(Map<String, Object> map);

    /**
     * 部门：通信与企业互联事业部
     * 功能：插入 登录  操作  access日志
     * 描述：
     * 作成者：leihao
     * 作成时间：2018-5-29
     */
    void loginLogsToDb(SysLogLogin log);

    void operLogsToDb(SysLogOper log);

    void accessLogsToDb(SysLogAccess log);


    /**
     * 部门：通信与企业互联事业部
     * 功能：日志查询  登录  操作  access日志
     * 描述：
     * 作成者：leihao
     * 作成时间：2018-5-29
     */
    Grid<Map<String, String>> findLoginLogList(Map<String, Object> map);

    Grid<Map<String, String>> findOperLogList(Map<String, Object> map);

    Grid<Map<String, String>> findAccessLogList(Map<String, Object> map);
}
