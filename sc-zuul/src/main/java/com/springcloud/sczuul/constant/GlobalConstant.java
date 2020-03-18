package com.springcloud.sczuul.constant;

import org.springframework.web.context.WebApplicationContext;

/**
 * 全局常量
 *
 * @dept 上海软件研发中心
 * @author HaoXin.Liu
 * @date 2020/3/9 21:19
 **/
public interface GlobalConstant {
    /**
     * 当前登录用户
     */
    String LOGIN_USER = "LOGIN_USER";
    /**
     * URI连接符号
     */
    String URL_CONNECT_SYMBOL = "/";
    /**
     * 一个用户登录存储ip的时间一天
     */
    int LOGIN_EX_SECOUNDS = 86400;
    /**
     * 用户登录账号
     */
    String LOGIN_USER_CODE = "login_user_code";

    /**
     * 当前登录用户岗位
     */
    String LOGIN_USER_CURRENT_POST = "LOGIN_USER_CURRENT_POST";
    String MENU_ROOT = "0";

    /**
     * 根 部门
     */
    String DEPARTMENT_ROOT = "0";

    /**
     * 根部门 自身代码
     */
    String DEPARTMENT_ROOT_DEPT_DM = "1";
    /**
     * 默认密码
     */
    String DEFAULT_PWD = "123456";

    String USER_MENU_ROOT = "0";


    /**
     * 超级管理员标记 1 是
     */
    String GLY_BJ = "1";

    /**
     * 普通用户标记
     */
    String GLY_BJ_N = "0";


    /**
     * 作废标记 1 作废
     */
    char ZF_BJ = '1';

    /**
     * 作废标记 0 不作废
     */
    String ZF_BJ_N = "0";

    /**
     * 装载spring文件
     */
    WebApplicationContext WEB = null;


    /**
     * 日志专用
     */
    String LOGIN = "login";
    String LOGOUT = "logout";
    String SUCCESS = "success";
    String FAIL = "fail";

    String APPID = "wisdom_campus";
    String APPLICATIONNAME = "wisdom_campus";
    /**
     * login类型
     */
    String METHODACCESS = "2";
    /**
     * access 类型
     */
    String METHODLOGIN = "1";
    /**
     * 操作类型
     */
    String METHODOPERA = "3";

}
