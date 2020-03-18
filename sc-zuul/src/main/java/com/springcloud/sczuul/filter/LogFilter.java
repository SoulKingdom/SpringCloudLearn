package com.springcloud.sczuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.springcloud.sczuul.constant.GlobalConstant;
import com.springcloud.sczuul.constant.OperaConstant;
import com.springcloud.sczuul.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 *  @dept 上海软件研发中心
 *  @description 日志记录
 *  @author HaoXin.Liu
 *  @date 2020/2/26 17:52
 **/
@Order()
@Component
public class LogFilter extends ZuulFilter {
    public static final Logger log = LoggerFactory.getLogger(LogFilter.class);


    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("日志记录操作");
        try {
            //获取前端请求的具体参数
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            HttpServletResponse response = ctx.getResponse();
            //获取对应参数
            String params = request.getParameter("data");
            //操作日志
            String operationType = getControllerMethodType(request);
            //判断你链接是否需要继续操作类型
            if ("".equals(operationType.trim())) {
                return null;
            }
            //把前端获取的数据放入map中
            Map<String, String> map = new HashMap<>(2);
            map.put("params", params);
            map.put(OperaConstant.OPERATION_TYPE, operationType);
            //操作信息
            LogUtil.operAspect(request, null, map, response);
            return null;
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("LogRecode IO异常", e);
        }
        return null;
    }


    /**
     * 得到 控制层对应的类型的分类，增删改查
     * @return
     */
    public static String getControllerMethodType(HttpServletRequest request) {
        //获取请求链接路径
        String methodName = request.getServletPath();
        //判断是否存在路径名
        if (!("".equals(methodName) && null == methodName)) {
            //判断链接路径对应地址最后一个 / 后的地址
            String[] str = methodName.split(GlobalConstant.URL_CONNECT_SYMBOL);
            methodName = str[str.length - 1];
        }

        String operaMethodType;
        //add  mod  del update save insert  remove  edit change  set
        if (null != methodName) {
            //循环判断常量数据的操作类型 增加操作关键字
            for (String obj : OperaConstant.ADD_OPEAR_FIELD) {
                if (methodName.startsWith(obj)) {
                    operaMethodType = OperaConstant.CONTROLLER_OPEAR_METHOD_TYPE_ADD;
                    return operaMethodType;
                }
            }
            //删除操作关键字
            for (String obj : OperaConstant.DEL_OPEAR_FIELD) {
                if (methodName.startsWith(obj)) {
                    operaMethodType = OperaConstant.CONTROLLER_OPEAR_METHOD_TYPE_DEL;
                    return operaMethodType;
                }
            }
            //修改操作关键字
            for (String obj : OperaConstant.ALTER_OPEAR_FIELD) {
                if (methodName.startsWith(obj)) {
                    operaMethodType = OperaConstant.CONTROLLER_OPEAR_METHOD_TYPE_MOD;
                    return operaMethodType;
                }
            }
            //上传操作关键字
            for (String obj : OperaConstant.UPLOAD_OPEAR_FIELD) {
                if (methodName.startsWith(obj)) {
                    operaMethodType = OperaConstant.CONTROLLER_OPEAR_METHOD_TYPE_UPLOAD;
                    return operaMethodType;
                }
            }
            //下载操作关键字
            for (String obj : OperaConstant.DOWN_OPEAR_FIELD) {
                if (methodName.startsWith(obj)) {
                    operaMethodType = OperaConstant.CONTROLLER_OPEAR_METHOD_TYPE_DOWNLOAD;
                    return operaMethodType;
                }
            }
        }
        return "";
    }
}