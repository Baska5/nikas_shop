package com.exadel.nikas_shop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AspectConfig {

    private final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Before(value = "execution(* com.exadel.nikas_shop.controller.*.*(..))")
    public void logStatementBeforeUserController(JoinPoint joinPoint) {
        logger.info("Executing {} args: {}", joinPoint, joinPoint.getArgs());
    }

    @After(value = "execution(* com.exadel.nikas_shop.controller.*.*(..))")
    public void logStatementAfterUserController(JoinPoint joinPoint) {
        logger.info("Complete execution of {}", joinPoint);
    }

    @Before(value = "execution(* com.exadel.nikas_shop.service.*.*(..))")
    public void logStatementBeforeUserService(JoinPoint joinPoint) {
        logger.info("Executing {} args: {}", joinPoint, joinPoint.getArgs());
    }

    @After(value = "execution(* com.exadel.nikas_shop.service.*.*(..))")
    public void logStatementAfterUserService(JoinPoint joinPoint) {
        logger.info("Complete execution of {}", joinPoint);
    }
}
