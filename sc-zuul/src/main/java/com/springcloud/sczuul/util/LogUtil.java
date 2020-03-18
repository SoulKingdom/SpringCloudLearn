package com.springcloud.sczuul.util;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.springcloud.sczuul.constant.GlobalConstant;
import com.springcloud.sczuul.constant.OperaConstant;
import com.springcloud.sczuul.log.dao.OperaLogDao;
import com.springcloud.sczuul.log.entity.OperaLogDO;
import com.springcloud.sczuul.log.entity.UserToken;
import com.util.util.request.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 日志工具
 *
 * @dept 上海软件研发中心
 * @author HaoXin.Liu
 * @date 2020/3/9 21:22
 **/
@Component
public class LogUtil {

    public static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5, 100, 1000,
            TimeUnit.MICROSECONDS,
            new LinkedBlockingQueue<>(1024),
            new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());

    private static OperaLogDao client;

    @Resource
    private OperaLogDao operaLogDao;

    @PostConstruct
    public void init() {
        client = operaLogDao;
    }


    @SuppressWarnings("unchecked")
    public static void operAspect(HttpServletRequest request, Exception ex, Map<String, String> map, HttpServletResponse response) {
        //获取request中的token值
        HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) request;
        String token = httpServletRequestWrapper.getRequest().getParameter(OperaConstant.ACCESS_TOKEN);
        //token非空校验
        if (StringUtils.isBlank(token)) {
            //没有获取token 不存在token，操作失败
            logger.info("========token数据获取失败========");
            return;
        }
        //掉token进行jwt解析
        /*UserToken userToken = getUserToken(token);*/
        UserToken userToken = new UserToken();
        //获取用户名和密码
        Map<String, String> loginUser = new HashMap<>(2);
        loginUser.put(OperaConstant.USER_CODE, userToken.getUserId());
        loginUser.put(OperaConstant.USER_NAME, userToken.getUsername());
        //获取请求参数
        String operaType = map.get(OperaConstant.OPERATION_TYPE);
        //用户是否存在  存在进入操作
        if (loginUser != null && null != loginUser.get(OperaConstant.USER_CODE)) {
            //设置操作日志基本信息
            OperaLogDO oper = setBasicOperaLog(request, ex, map, loginUser, operaType, response);
            //开启异步日志记录 线程池操作执行
            executor.execute(new SaveOperaThread(oper));
            logger.info("=======操作日志线程池异步执行=========");
        }
    }

    /**
     * 获得token的解信息
     *
     * @dept 上海软件研发中心
     * @author HaoXin.Liu
     * @date 2020/3/7 13:36
     **/
   /* public static UserToken getUserToken(String token) {
        UserToken userToken = new UserToken();
        String id = SecurityUtils.getUserId(token);
        userToken.setUserId(id);
        userToken.setUsername(getUserName(id));
        return userToken;
    }*/
    private static String getUserName(String userId) {
        //根据userId查找userName
        String userName = client.getUserName(userId);
        return userName;
    }

    /**
     * 操作日志数据录入
     *
     * @dept 上海软件研发中心
     * @param request 前端请求
     * @param loginUser 登陆信息
     * @param operaType 操作类型
     * @return
     * @author HaoXin.Liu
     * @date 2020/2/28 10:50
     **/
    private static OperaLogDO setBasicOperaLog(HttpServletRequest request, Exception
            ex, Map<String, String> map, Map<String, String> loginUser, String operaType, HttpServletResponse response) {

        OperaLogDO oper = new OperaLogDO();
        //随机生成code编码
        oper.setAppId(GlobalConstant.APPID);
        oper.setMethod(GlobalConstant.METHODOPERA);

        oper.setUserCode(loginUser.get(OperaConstant.USER_CODE));
        oper.setCreateBy(loginUser.get(OperaConstant.USER_CODE));
        oper.setUserName(loginUser.get(OperaConstant.USER_NAME));
        oper.setLastModifiedBy(loginUser.get(OperaConstant.USER_CODE));

        oper.setSourceType("web");
        oper.setSourceApplication(request.getHeader("User-Agent"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        oper.setOperationTime(format.format(new Date()));
        oper.setSourcePublicIp(RequestUtils.getRemoteAddr(request));
        oper.setSourcePrivateIp(getRemoteAddr(request));


        oper.setOperationType(operaType);

        oper.setResult("");
        //判断过滤链接是否是404
        if (HttpStatus.SC_OK == response.getStatus()) {
            oper.setReason("操作成功");
        } else {
            oper.setReason("操作失败");
        }

        oper.setContent("");

        return oper;
    }

    /**
     * 获取路径远程ip地址
     *
     * @dept 上海软件研发中心
     * @return String
     * @author HaoXin.Liu
     * @date 2020/2/29 12:05
     **/
    private static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("Proxy-Client-IP");
        } else if (StringUtils.isNotBlank(remoteAddr)) {
            remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
    }


    /**
     * 部门：日志信息异步保存
     * 功能：opera 异步保存
     * 描述：略
     * 作成者：HaoXin.Liu
     * 作成时间：2018-5-29
     */
    public static class SaveOperaThread extends Thread {

        private OperaLogDO log;

        public SaveOperaThread(OperaLogDO log) {
            super(SaveOperaThread.class.getSimpleName());
            this.log = log;
        }

        @Override
        public void run() {
            // 如果有异常，设置异常信息
            client.insert(log);
        }
    }
}
