package com.springcloud.sczuul.aopproj.entity;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;            //id
    private String title;        // 日志标题
    private String remoteAddr;    // 操作用户的IP地址
    private String requestUri;    // 操作的URI
    private String method;        // 操作的方式
    private String userCode;    // 操作用户代理信息
    private String userName;    // 用户名
    private String deptCode;    //部门code
    private String deptName;    //部门名称
    private String exception;    // 异常信息
    private String userAgent;    // 工具
    private String type; //日志类型 1 正常日志  2 异常日志
    private Date gmtCreate;        // 日期

    private String params; //请求参数


    // 日志类型（1：接入日志；2：错误日志）
    public static final String TYPE_ACCESS = "1";
    public static final String TYPE_EXCEPTION = "2";

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "Log [title=" + title + ", remoteAddr=" + remoteAddr
                + ", requestUri=" + requestUri + ", method=" + method
                + ", userCode=" + userCode + ", exception=" + exception
                + ", userAgent=" + userAgent + ", type=" + type
                + ", gmtCreate=" + gmtCreate + "]";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
