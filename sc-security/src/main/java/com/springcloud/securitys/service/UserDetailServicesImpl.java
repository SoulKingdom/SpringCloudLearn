package com.springcloud.securitys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description TODO
 *  @author HaoXin.Liu
 *  @date 2020/1/7 14:44
 **/
@Service("userDetailsService")
public class UserDetailServicesImpl implements UserDetailsService {
    public static final Logger log = LoggerFactory.getLogger(UserDetailServicesImpl.class);

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("用户名是：" + userName);
        //密码要与我查出来的匹配一致
        String password = passwordEncoder.encode("123456");
        log.info("数据库中密码是：" + password);
        //返回对应用户名和密码
        return new User(userName,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,user"));
    }
}