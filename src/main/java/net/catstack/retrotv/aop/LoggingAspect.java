package net.catstack.retrotv.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("@annotation(net.catstack.retrotv.annotations.LoggingAspect)")
    public void callAtLoggingAspectAnnotation() {}

    @Before("callAtLoggingAspectAnnotation()")
    public void before(final JoinPoint jp) {
        var args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        log.info("Starting method " + jp.getSourceLocation().getWithinType().getSimpleName() + "." + jp.getSignature().getName()
                + "(), args=[" + args + "]");
    }

    @AfterReturning(value = "callAtLoggingAspectAnnotation()", returning = "retVal")
    public void after(final JoinPoint jp, final Object retVal) {
        log.info("Complete method " + jp.getSourceLocation().getWithinType().getSimpleName() + "." + jp.getSignature().getName()
                + "(), with result: " + retVal);
    }
}
