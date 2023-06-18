package com.zh.springo.config;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zh.springo.redisConfig.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: zbxComputer
 * @time: 2023/4/7 15:18
 */
@Component
public class UserConfig implements HandlerInterceptor {
    @Resource
    private RedisUtil redisUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Object o = redisUtil.get(token);
        if (ObjectUtils.isNotEmpty(o)) {
            return true;
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("用户未登录");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
