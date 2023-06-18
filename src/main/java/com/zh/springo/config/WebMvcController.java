package com.zh.springo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @description:
 * @author: zbxComputer
 * @time: 2023/4/7 15:15
 */
@Configuration
public class WebMvcController implements WebMvcConfigurer {
    @Resource
    private UserConfig config;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(config).addPathPatterns("/orders/**");
    }
}
