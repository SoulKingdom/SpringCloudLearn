package com.neusoft.gateway.log.entity;

/**
 *  @dept 上海软件研发中心
 *  @description 日志控制条件
 *  @author HaoXin.Liu
 *  @date 2020/2/29 14:08
 **/
public class LogCond {
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 开始时间
     */
    private String logStartTime;
    /**
     * 结束时间
     */
    private String logEndTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogStartTime() {
        return logStartTime;
    }

    public void setLogStartTime(String logStartTime) {
        this.logStartTime = logStartTime;
    }

    public String getLogEndTime() {
        return logEndTime;
    }

    public void setLogEndTime(String logEndTime) {
        this.logEndTime = logEndTime;
    }


    @Override
    public String toString() {
        return "LogCond{" +
                "userName='" + userName + '\'' +
                ", logStartTime='" + logStartTime + '\'' +
                ", logEndTime='" + logEndTime + '\'' +
                '}';
    }
}