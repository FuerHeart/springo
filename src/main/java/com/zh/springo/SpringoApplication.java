package com.zh.springo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = "com.zh.springo.mapper")
public class SpringoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringoApplication.class, args);
    }

}
