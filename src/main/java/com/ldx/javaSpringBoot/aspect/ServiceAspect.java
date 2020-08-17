package com.ldx.javaSpringBoot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAspect {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServiceAspect.class);

    @Pointcut("@annotation(com.ldx.javaSpringBoot.aspect.ServiceAnnotation)")
    @Order(2)
    public void servicePointCut(){}

    @Before(value = "com.ldx.javaSpringBoot.aspect.ServiceAspect.servicePointCut()")
    public void beforeService(JoinPoint joinpoint) {
        LOGGER.debug("==== This is before service ====");
    }

    @Around(value = "com.ldx.javaSpringBoot.aspect.ServiceAspect.servicePointCut()")
    public Object aroundService(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        LOGGER.debug("==== This is around service ==== ");
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    @After(value = "com.ldx.javaSpringBoot.aspect.ServiceAspect.servicePointCut()")
    public void afterService() {
        LOGGER.debug("==== This is after service ====");
    }
}
