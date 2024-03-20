package net.gvsun.job;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited // 表明这个注解可以被继承
public @interface GvsunJob {
    String value() ; //任务标识
    String param() default ""; //任务参数
    String desc() default "定时任务demo"; //任务描述
    String cron() default "0 0 0 * * ?"; //任务执行注册时时间表达式
}

