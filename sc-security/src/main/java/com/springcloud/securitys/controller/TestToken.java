package com.springcloud.securitys.controller;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;

import java.util.Map;

/**
 *  @dept 上海软件研发中心
 *  @description Token 解析
 *  @author HaoXin.Liu
 *  @date 2020/3/23 14:08
 **/
public class TestToken {
    private  static JsonParser jsonParser = JsonParserFactory.create();

    public static void main(String[] args) {
        //解析token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBwIiwib2F1dGgyLXJlc291cmNlIiwid2ViIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU4NDUyOTYzNywiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI1ZDg4ZTg0NS02ZjQ3LTQyMGYtYmNhOS04ZjNkODdjODY4ZGMiLCJjbGllbnRfaWQiOiJ3ZWJhcHAiLCJYLUFVVEgtVXNlcklkIjoiMSJ9.fcdDRH0AhXyMJkE60nogJgA7KOHL3uuX9qyOCb1lqlk";
        Jwt jwt = JwtHelper.decode(token);
        String content = jwt.getClaims();
        Map<String,Object> map =jsonParser.parseMap(content);
        Object userId = map.get("X-AUTH-UserId");
        System.out.println(userId);
    }
}