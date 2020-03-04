package com.springcloud.securitys.config;

import com.springcloud.securitys.token.filter.LindTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 *  @dept 上海软件研发中心
 *  @description TODO
 *  @author HaoXin.Liu
 *  @date 2020/1/7 9:48
 **/
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * token过滤器.
     */
    @Resource
    LindTokenAuthenticationFilter lindTokenAuthenticationFilter;

    /**
     * 用户名管理配置
     */
   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //下面配置两个用户
        auth.inMemoryAuthentication().withUser("starry").password("123456").roles("admin");
    }*/


    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码加密工具,用来控制加密方式
     *
     * @dept 上海软件研发中心
     * @return 密码加密工具
     * @author HaoXin.Liu
     * @date 2020/1/7 9:58
     **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 认证提供者：提供认证信息
     *
     * @dept 上海软件研发中心
     * @return
     * @author HaoXin.Liu
     * @date 2020/1/7 16:03
     **/
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //认证对象创建
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //用户信息获取
        authenticationProvider.setUserDetailsService(userDetailsService);
        //设置密码的编码类型
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    /**
      * 设置授权方式
      *
      * @dept 上海软件研发中心
      * @param httpSecurity
      * @author HaoXin.Liu
      * @date 2020/1/7 17:44
      **/
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/lind-auth/**").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        httpSecurity
                //添加token验证
                .addFilterBefore(lindTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

    /**
     * 设置构建器
     *
     * @dept 上海软件研发中心
     * @param auth  AuthenticationManagerBuilder 为了构建AuthenticationManager对象
     * @return
     * @author HaoXin.Liu
     * @date 2020/1/7 15:55
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //生成构建用户信息
        auth.userDetailsService(userDetailsService);
        //提供者信息
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * auth2
     *
     * @dept 上海软件研发中心
     * @return
     * @author HaoXin.Liu
     * @date 2020/1/7 16:48
     **/
   /* @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

*/
    /**
     * 密码加密工具,用来控制加密方式
     *
     * @dept 上海软件研发中心
     * @return 密码加密工具
     * @author HaoXin.Liu
     * @date 2020/1/7 9:58
     **/
   /* @Bean
    PasswordEncoder passwordEncoder() {
        //过时类，表示不加密密码
        return  NoOpPasswordEncoder.getInstance();
    }*/
}