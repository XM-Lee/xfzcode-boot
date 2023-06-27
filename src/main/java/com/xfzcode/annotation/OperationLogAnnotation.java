package com.xfzcode.annotation;

import java.lang.annotation.*;

/**
 * @Author: XMLee
 * @Date: 2023/6/27 16:03
 * @Description: 关键操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogAnnotation {
    /**
     * 描述
     * @return
     */
    String description();


    String entityParam() default "";
    /**
     * 对应参数实体
     * @return
     */
    String[] detailParam() default {};

}
