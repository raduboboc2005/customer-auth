package com.rb.authentication.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import com.google.common.base.Throwables;

import static com.rb.authentication.aop.LoggingBean.ApiType.DB;
import static com.rb.authentication.aop.LoggingBean.Status.FAILURE;
import static com.rb.authentication.aop.LoggingBean.Status.SUCCESS;

@Aspect
@Component
public class LoggingAspect {
    public static final String LOGGING_STATS = "logging.DummyAuthenticationStatus";

    private static final String LOGGING_SUCCESS_MESSAGE = "Successfully completed %s call";
    private static final Logger LOGGER = LoggerFactory.getLogger(LOGGING_STATS);

    protected final ThreadLocal<LoggingBean.LoggingBeanBuilder> controllerMessageBuilder = new ThreadLocal<>();
    private static final ThreadLocal<Long> CONTROLLER_START_TIME = new ThreadLocal<>();

    @Pointcut(value = "@within(org.springframework.stereotype.Controller)")
    public void getAllControllers() {
        //NOSONAR
    }

    @Pointcut("execution(public * com.rb.authentication.repository.*.*(..))")
    public void getExecutionOfAllRepositoryCalls() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before(value = "getAllControllers()")
    public void beforeControllerCall(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        completeLoggerMessageBuilder(joinPoint, annotations);
    }

    @AfterReturning(value = "getAllControllers()", returning = "returnValue")
    public void afterReturningRestControllerCall(Object returnValue) {
        if (controllerMessageBuilder.get() != null) {
            controllerMessageBuilder.get().durationMs(getDurationElapsedFrom(CONTROLLER_START_TIME.get())).status(SUCCESS);
            LOGGER.info(controllerMessageBuilder.get().build().toString());
            controllerMessageBuilder.get().returnValue(returnValue);
            LOGGER.debug(controllerMessageBuilder.get().build().toString());

            controllerMessageBuilder.remove();
            CONTROLLER_START_TIME.remove();
        }
    }

    @AfterThrowing(value = "getAllControllers()", throwing = "ex")
    public void afterThrowingRestControllerCall(Throwable ex) {//NOSONAR
        if (controllerMessageBuilder.get() != null) {
            controllerMessageBuilder.get()
                    .detailMessage(ex.getMessage())
                    .status(FAILURE)
                    .statusCode(getStatusCodeFromException(ex))
                    .stackTrace(Throwables.getStackTraceAsString(ex))
                    .durationMs(getDurationElapsedFrom(CONTROLLER_START_TIME.get()));
            LOGGER.error(controllerMessageBuilder.get().build().toString());
            controllerMessageBuilder.remove();
            CONTROLLER_START_TIME.remove();
        }
    }

    @Around("getExecutionOfAllRepositoryCalls()")
    public Object aroundAllRepositoryCallsInvocation(ProceedingJoinPoint joinPoint) throws Throwable {//NOSONAR
        LoggingBean.LoggingBeanBuilder loggingBeanBuilder = LoggingBean.builder()
                .apiType(DB)
                .action(((MethodSignature) joinPoint.getSignature()).getMethod().getName())
                .detailMessage(String.format(LOGGING_SUCCESS_MESSAGE, DB.getType()))
                .className(getClassName(joinPoint.getSignature().getDeclaringTypeName()))
                .method(getMethodName(joinPoint.getSignature()))
                .arguments(joinPoint.getArgs());

        return tryJoinPointProceed(joinPoint, loggingBeanBuilder);
    }

    private Object tryJoinPointProceed(ProceedingJoinPoint joinPoint, LoggingBean.LoggingBeanBuilder loggingBeanBuilder) throws Throwable { //NOSONAR
        long startTime = System.currentTimeMillis();
        try {
            return joinPointProceed(joinPoint, loggingBeanBuilder, startTime);
        } catch (Throwable e) { //NOSONAR
            loggingBeanBuilder.durationMs(getDurationElapsedFrom(startTime));
            loggingBeanBuilder.status(FAILURE);
            loggingBeanBuilder.detailMessage(e.getMessage());

            throw e;
        } finally {
            LoggingBean loggingBean = loggingBeanBuilder.build();
            if (loggingBean.getStatus().equals(FAILURE)) {
                LOGGER.error(loggingBean.toString());
            } else if (loggingBeanBuilder.build().getStatus().equals(SUCCESS)) {
                LOGGER.info(loggingBean.toString());
            }
        }
    }

    private Object joinPointProceed(ProceedingJoinPoint joinPoint, LoggingBean.LoggingBeanBuilder loggingBeanBuilder, long startTime) throws Throwable { //NOSONAR
        Object result = joinPoint.proceed();

        loggingBeanBuilder.durationMs(getDurationElapsedFrom(startTime));
        loggingBeanBuilder.status(SUCCESS);

        return result;
    }

    private String getStatusCodeFromException(Throwable e) {

        try {
            if (e instanceof HttpRequestMethodNotSupportedException || e instanceof MethodArgumentTypeMismatchException
                    || e instanceof MethodArgumentNotValidException
                    || e instanceof ConstraintViolationException
                    || e instanceof HttpMessageNotReadableException
                    || e instanceof IllegalArgumentException
                    || e instanceof DataAccessException) {
                return String.valueOf(HttpStatus.BAD_REQUEST.value());
            }

            return ((HttpStatusCodeException) e).getStatusCode().toString();
        } catch (Exception ex) {
            return String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void completeLoggerMessageBuilder(JoinPoint joinPoint, Annotation[][] annotations) {
        LoggingBean.LoggingBeanBuilder loggingBeanBuilder = LoggingBean.builder();
        controllerMessageBuilder.set(loggingBeanBuilder);

        controllerMessageBuilder.get().apiType(LoggingBean.ApiType.CONTROLLER)
                .action(((MethodSignature) joinPoint.getSignature()).getMethod().getName())
                .className(getClassName(joinPoint.getSignature().getDeclaringTypeName()))
                .method(getMethodName(joinPoint.getSignature()))
                .arguments(joinPoint.getArgs());

        CONTROLLER_START_TIME.set(System.currentTimeMillis());
    }

    private String getClassName(String signature) {
        return signature.substring(signature.lastIndexOf('.') + 1);
    }

    private String getMethodName(Signature signature) {
        String methodName = signature.getName();
        String stringSignature = signature.toString();
        String arguments = stringSignature.substring(stringSignature.indexOf('('));
        return methodName + "\", with arguments type=\"" + arguments;
    }

    private long getDurationElapsedFrom(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}
