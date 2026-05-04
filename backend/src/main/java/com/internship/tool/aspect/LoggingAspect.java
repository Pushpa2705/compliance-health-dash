package com.internship.tool.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.internship.tool.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[" + LocalDateTime.now() + "] ➡️ Entering: " 
                + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* com.internship.tool.service.*.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        System.out.println("[" + LocalDateTime.now() + "] ✅ Exiting: " 
                + joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.internship.tool.service.*.*(..))", throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) {
        System.out.println("[" + LocalDateTime.now() + "] ❌ Error in: " 
                + joinPoint.getSignature().getName());
        System.out.println("⚠️ Exception: " + ex.getMessage());
    }
}