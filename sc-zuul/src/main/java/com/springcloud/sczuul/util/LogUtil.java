package com.springcloud.sczuul.util;


import com.springcloud.sczuul.constant.GlobalConstant;
import com.springcloud.sczuul.log.dao.OperaLogDao;
import com.springcloud.sczuul.log.entity.OperaLogDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class LogUtil {

    public static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    private static OperaLogDao client;

    @Resource
    private OperaLogDao operaLogDao;

    @PostConstruct
    public void init() {
        client = operaLogDao;
    }


    @SuppressWarnings("unchecked")
    public static void operAspect(HttpServletRequest request, Exception ex, Map<String, String> map, OperaLogDO oper) {
        HttpSession session = request.getSession();
        //模拟session获取session的是对象 SecurityUtils.getCurrentUserId();
        Map<String, String> loginUser = (Map<String, String>) session.getAttribute(GlobalConstant.LOGIN_USER);
        //获取请求参
        String operaType = map.get("operationType");
        //用户是否存在  存在进入操作
        if (loginUser != null && null != loginUser.get("USER_CODE")) {
            //设置操作日志基本信息
            OperaLogDO opera = setBasicOperaLog(request, ex, map, loginUser, operaType,oper);
            //开启异步日志记录
            new SaveOperaThread(opera).start();
        }
    }

    /**
     * TODO
     *
     * @dept 上海软件研发中心
     * @param request 前端请求
     * @param loginUser 登陆信息
     * @param operaType 操作类型
     * @return
     * @author HaoXin.Liu
     * @date 2020/2/28 10:50
     **/
    private static OperaLogDO setBasicOperaLog(HttpServletRequest request, Exception ex, Map<String, String> map, Map<String, String> loginUser, String operaType, OperaLogDO oper) {
        //随机生成code编码
        oper.setAppId(GlobalConstant.APPID);
        //Todo 部门信息是否需要
       /* oper.setStation(loginUser.get("DEPT_NAME"));
        oper.setStationCode(loginUser.get("DEPT_CODE"));*/

        oper.setProvince("");
        oper.setCity("");
        oper.setUserCode(loginUser.get("USER_CODE"));
        oper.setUserName(loginUser.get("USER_NAME"));
        oper.setCreateBy(loginUser.get("USER_CODE"));

        oper.setSourceType("web");
        oper.setSourceApplication(request.getHeader("User-Agent"));
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
        oper.setOperationTime(format.format(new Date()));

        oper.setSourcePublicIp(null == request.getHeader("X-Real-IP") ? "" : request.getHeader("X-Real-IP"));
        oper.setSourcePrivateIp(getRemoteAddr(request));
        oper.setVpn("");

        oper.setSourceMac("");
        oper.setTargetApplication("");
        oper.setTargetPrivateIp("");
        oper.setTargetPublicIp("");
        oper.setOperationType(operaType);

        oper.setResult("");
        oper.setReason("");
        oper.setContent("");
        oper.setEmail("");

        return oper;
    }


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
     * 作成者：leihao
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
            client.insertSelective(log);
        }
    }
}