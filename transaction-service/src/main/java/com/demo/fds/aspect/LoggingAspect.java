package com.demo.fds.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.demo.fds.controller..*.*(..)) || " +
            "execution(* com.demo.fds.service..*.*(..)) || " +
            "execution(* com.demo.fds.repository..*.*(..))" +
            "execution(* com.demo.fds.config..*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // Method signature details
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        // Log method entry
        log.debug("Entering {}.{}() with arguments: {}", className, methodName, Arrays.toString(args));

        // Measure execution time
        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in {}.{}(): {}", className, methodName, throwable.getMessage());
            throw throwable;
        }
        long executionTime = System.currentTimeMillis() - startTime;

        // Log method exit
        if (methodName.equals("cacheManager") || methodName.equals("dataSource")) {
            log.info("Initialized {} with result: {}", methodName, result);
        } else {
            log.debug("Exiting {}.{}(). Execution time: {} ms, Result: {}",
                    className, methodName, executionTime, result);
        }

        return result;
    }
}