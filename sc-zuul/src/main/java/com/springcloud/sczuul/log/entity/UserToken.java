package com.springcloud.sczuul.log.entity;

/**
 *  @dept 上海软件研发中心
 *  @description Token保存的用户信息
 *  @author HaoXin.Liu
 *  @date 2020/3/6 19:26
 **/
public class UserToken {
    /**
     * 密码
     */
    private String userId ;
    /**
     * 用户名
     */
    private String username;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}