package com.nbkj.bootaop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * MyLogAspect
 */
@Aspect
@Component
public class MyLogAspect {

    @Pointcut("@annotation(com.springboot.annotation.Log)")
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
    }
    
}