package com.eureka.aop.aopproj.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 切点类 只做对 增删改的 aop 拦截    --opera
 */
@Aspect
@Component
@Order(2)
public class SystemLogAspect {

    /**
     * 本地异常日志记录对象
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(SystemLogAspect.class);

    /**
     * 部门：通信与企业互联事业部
     * 功能：切点
     * 描述：
     * 作成者：leihao
     * 作成时间：2018-6-1
     */
    // Controller层切点
    @Pointcut("execution (* com.eureka.*.*.controller..*.add*(..)) || "
            + "execution (* com.eureka.*.*.controller..*.mod*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.del*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.update*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.save*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.insert*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.remove*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.edit*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.create*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.upload*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.load*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.down*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.excel*(..)) ||"
            + "execution (* com.eureka.*.*.controller..*.change*(..)) "
            + "execution (* com.eureka.*.*.controller..*.set*(..)) ")
    public void controllerAspect() {
    }

    /**
     * 部门：通信与企业互联事业部
     * 功能：前通知
     * 描述：
     * 作成者：leihao
     * 作成时间：2018-6-1
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            //获取前端请求参数名称
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            //前端描述名称
            String title = getControllerMethodDescription(joinPoint);
            //前端接受的参数
            String params = request.getParameter("data");

            //操作日志
            String operationType = getControllerMethodType(joinPoint);
            Map<String, String> map = new HashMap<String, String>();
            map.put("params", params);
            map.put("title", title);
            map.put("operationType", operationType);
            //TODO
            //LogUtil.operAspect(request, null, map);

            //LogUtil.logsToDbAspect(request, null, map);
        } catch (Exception e) {
            // 记录本地异常日志
            LOGGER.error("==前置通知异常==");
            LOGGER.error("异常信息:{}", e.getMessage());
        }
    }


    /**
     * 部门：通信与企业互联事业部
     * 功能：异常通知
     * 描述：
     * 作成者：leihao
     * 作成时间：2018-6-1
     */
    @AfterThrowing(value = "controllerAspect()", throwing = "e")
    public void afterThorwingMethod(JoinPoint jp, NullPointerException e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Map<String, String> map = new HashMap<String, String>();
        map.put("operationType", "");
        //TODO
//		LogUtil.operAspect(request, null, map);
    }

    /**
     * 部门：通信与企业互联事业部
     * 功能： 获取注解中对方法的描述信息 用于Controller层注解
     * 描述：
     * 作成者：leihao
     * 作成时间：2018-6-1
     */
    @SuppressWarnings("rawtypes")
    public static String getControllerMethodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        //TODO
      /*  for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    if (null == method.getAnnotation(SystemControllerLog.class)) {
                        return "";
                    }

                    description = method.getAnnotation(
                            SystemControllerLog.class).description();
                    break;
                }
            }
        }*/
        return description;
    }

    /**
     * 得到 调用的分类，增删改查
     * @return
     */
    public static String getControllerMethodType(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String operaMethodType = "";
        if (null != methodName) {//add  mod  del update save insert  remove  edit change  set
            if (methodName.startsWith("add") || methodName.startsWith("save") || methodName.startsWith("insert") || methodName.startsWith("create")) {
                operaMethodType = "新增";
                return operaMethodType;
            }
            if (methodName.startsWith("del") || methodName.startsWith("remove")) {
                operaMethodType = "删除";
                return operaMethodType;
            }
            if (methodName.startsWith("mod") || methodName.startsWith("update") || methodName.startsWith("edit") || methodName.startsWith("set")) {
                operaMethodType = "修改";
                return operaMethodType;
            }
            if (methodName.startsWith("upload") || methodName.startsWith("fdfs") || methodName.startsWith("excelImp")) {
                operaMethodType = "上传";
                return operaMethodType;
            }
            if (methodName.startsWith("down")) {
                operaMethodType = "下载";
                return operaMethodType;
            }
        }
        return "";
    }
}
