package com.nbkj.bootaop.aspect;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import com.nbkj.bootaop.annotation.MyLog;
import com.nbkj.bootaop.domain.MyLogDo;
import com.nbkj.bootaop.utils.HttpContextUtils;
import com.nbkj.bootaop.utils.IPUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


/**
 * MyLogAspect
 */
@Aspect
@Component
public class MyLogAspect {

    @Pointcut("@annotation(com.nbkj.bootaop.annotation.MyLog)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public void around(ProceedingJoinPoint point) {
        // 秒表计时
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            // 执行方法
            point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        stopWatch.prettyPrint(); // 执行时长(毫秒)
        long totalTimeMillis = stopWatch.getTotalTimeMillis(); // 获取用毫秒
        aopHandle(point,totalTimeMillis);
    }

    private void aopHandle(ProceedingJoinPoint point, Long time) {
        Signature signature = point.getSignature(); // 获取切入的方法
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        MyLogDo myLog = new MyLogDo();
        MyLog annotation = method.getAnnotation(MyLog.class);
        if (Objects.nonNull(annotation)) {
            myLog.setOperation(annotation.value());
        }
        // 获取请求方法名字
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        myLog.setMethod(className + "." + methodName);

        // 请求参数
        Object[] args = point.getArgs();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = u.getParameterNames(method);
        if (Objects.nonNull(args) && Objects.nonNull(parameterNames)) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + parameterNames[i] + ": " + args[i];
            }
            myLog.setParams(params);
        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置ip
        myLog.setIp(IPUtils.getIpAddr(request));
        myLog.setUsername("BingShu");
        myLog.setTime(time);
        myLog.setCreateTime(new Date());

        System.out.println("===================");
         //对业务进行处理，数据分析，或者存储等
        System.out.println(myLog.toString());
        System.out.println("===================");
    }

}