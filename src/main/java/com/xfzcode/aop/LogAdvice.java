package com.xfzcode.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xfzcode.annotation.OperationLogAnnotation;
import com.xfzcode.pojo.OperationLog;
import com.xfzcode.service.OperationLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @Author: XMLee
 * @Date: 2023/4/27 14:48
 * @Description:
 */
@Aspect
@Component
public class LogAdvice implements ApplicationListener<ContextRefreshedEvent> {
    private BlockingQueue<OperationLog> operationLogQ = new LinkedBlockingDeque<>();

    @Autowired
    private OperationLogService operationLogService;



    private static final Logger LOGGER = LogManager.getLogger();

    @Pointcut("execution(public * com.xfzcode.controller..*.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object logAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        OperationLog log = new OperationLog();
        // 1 获取当前方式上是否有注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        Object proceed = joinPoint.proceed();
        if (annotation != null) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            log.setTime(new Date());
            HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
            log.setUrl(request.getRequestURI());
            log.setIp(request.getRemoteAddr());

            log.setAction(annotation.description());
            log.setEntity(annotation.entityParam());
            log.setDetail(Arrays.toString(annotation.detailParam()));
            String token = request.getHeader("Authorization");
            //TODO 拦截token 获取当前的操作的用户
            if (token != null && token.startsWith("Bearer ")) {

            }
            if (Objects.isNull(joinPoint.getArgs())) {
                log.setEntity(JSONObject.toJSONString(joinPoint.getArgs()));
            }
            String resultJson = JSONObject.toJSONString(proceed);
            JSONObject jsonObject = JSON.parseObject(resultJson);
            log.setStatusCode(jsonObject.get("code") + "");
            log.setStatus((String) jsonObject.get("msg"));
            operationLogQ.add(log);
        } else {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            log.setTime(new Date());
            HttpServletRequest request = Objects.requireNonNull(requestAttributes).getRequest();
            log.setUrl(request.getRequestURI());
            log.setIp(request.getRemoteAddr());
            log.setEntity(JSONObject.toJSONString(joinPoint.getArgs()));
            String resultJson = JSONObject.toJSONString(proceed);
            JSONObject jsonObject = JSON.parseObject(resultJson);
            log.setStatusCode(jsonObject.get("code") + "");
            log.setStatus((String) jsonObject.get("msg"));
            operationLogQ.add(log);
        }
        return proceed;
    }

    /**
     * consume queue 2 save db
     */
    private void saveLogs() {
        while (true) {
            try {
                OperationLog log = operationLogQ.poll(5, TimeUnit.MILLISECONDS);
                if (log != null) {
                    operationLogService.save(log);
                }
            } catch (Exception e) {
                LOGGER.error("insert operation log to db error", e);
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ExecutorService pool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("Save-Logs-%d").setDaemon(true).build());
        pool.submit(new Runnable() {
            @Override
            public void run() {
                saveLogs();
            }
        });
    }
}

