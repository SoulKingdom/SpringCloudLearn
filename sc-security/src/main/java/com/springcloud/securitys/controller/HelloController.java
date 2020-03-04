package com.springcloud.securitys.controller;

import com.springcloud.securitys.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description Hello控制层
 *  @author HaoXin.Liu
 *  @date 2020/1/6 14:48
 **/
@RestController
@RequestMapping("/lind-auth")
public class HelloController {
    @Resource
    RedisUtil redisUtil;
    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    UserDetailsService userDetailsService;
    @Resource
    RedisTemplate redisTemplate;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("login")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            @RequestParam String username,
            @RequestParam String password) throws AuthenticationException {
        return ResponseEntity.ok(generateToken(username, password));
    }

    /**
     * 登陆与授权.
     *
     * @param username .
     * @param password .
     * @return
     */
    private String generateToken(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 持久化的redis
        String token = (String) redisUtil.get(userDetails.getUsername());
        redisTemplate.opsForValue().set(token, userDetails.getUsername());
        return token;
    }
}