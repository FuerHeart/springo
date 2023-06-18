package com.zh.springo.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggingAspect {


    @Before("execution(public void com.zh.springo.MQ.OrderConsumer.onMessage())")
    public void sayHello(){
        System.out.println("hello");
    }
}
