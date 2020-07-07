package com.xpf.dubbo.dubbotool.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: xia.pengfei
 * @date: 2020/7/3
 */
@Component
@Slf4j
@Aspect
public class LogRecord {

    @Pointcut("@within(SelLog) || @annotation(SelLog)")
    private void doLog() {
    }

    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("class:{},method:{} start,params:{}", joinPoint.getTarget().getClass(),
            joinPoint.getSignature().getName(), JSON.toJSONString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        log.debug("class:{},method:{} end,result:{} ", joinPoint.getTarget().getClass(),
            joinPoint.getSignature().getName(), result);
        return result;
    }

    @AfterThrowing(value = "doLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        log.debug("class:{},method:{},exception:{}", joinPoint.getTarget().getClass(),
            joinPoint.getSignature().getName(), e);
    }

}
