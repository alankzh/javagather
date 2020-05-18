package alankzh.blog.base.core.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceAspect {

    @Pointcut("within(alankzh.blog..*)")
    public void withinProjectService() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void beAnnotatedWithService() {
    }

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {
    }

    @Around(value = "withinProjectService() && beAnnotatedWithService() && publicMethod()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String logName = targetName + "." + methodName;
        Object[] args = joinPoint.getArgs();

        log.info("begin {}, args:{}", logName, args);
        Object obj = joinPoint.proceed();
        log.info("end {}, result:{}", logName, obj);
        return obj;
    }

}
