package com.springcloud.securitys.token.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 主要实现了对请求的拦截，获取http头上的Authorization元素，token码就在这个键里，
 * 我们的token都是采用通用的Bearer开头，当你的token没有过期时，会存储在redis里，
 * key就是用户名的md5码，而value就是用户名，当拿到token之后去数据库或者缓存里拿用户信息进行授权即可。
 *
 *  @dept 上海软件研发中心
 *  @author HaoXin.Liu
 *  @date 2020/1/7 17:23
 **/
@Component
public class LindTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    String tokenHead = "Bearer ";
    String tokenHeader = "Authorization";

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取request的header
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // The part after "Bearer "
            final String authToken = authHeader.substring(tokenHead.length());
            //redis中是否有对应token
            if (authToken != null && redisTemplate.hasKey(authToken)) {
                //获取token对应的用户名
                String username = redisTemplate.opsForValue().get(authToken);
                //存在用户名并且不存在登录用户信息
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //获取用户信息
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    //可以校验token和username是否有效，目前由于token对应username存在redis，都以默认都是有效的
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    logger.info("authenticated user " + username + ", setting security context");
                    //设置上下文信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}