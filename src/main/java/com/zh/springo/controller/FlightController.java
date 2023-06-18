package com.zh.springo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zh.springo.config.Result;
import com.zh.springo.mapper.FlightMapper;
import com.zh.springo.pojo.FlightDomain;
import com.zh.springo.redisConfig.RedisUtil;
import com.zh.springo.service.FlightService;
import com.zh.springo.service.RecAlgorithmService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zbxComputer
 * @time: 2022/12/26 14:14
 */
@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RecAlgorithmService recAlgorithmService;

    public FlightController(@Qualifier(value = "flightServiceImpl") FlightService flightService) {
        this.flightService = flightService;
    }


    @PostMapping("/single")
    public Result singleFlightSearch(@RequestBody FlightDomain domain) throws JsonProcessingException {
        if (!ObjectUtils.isEmpty(domain)) {
            Result result = new Result(Result.SUCCESS, "成功");
            String key = domain.getDeparture() + domain.getArrival() + domain.getDepartureDate();
            Map<String, List<FlightDomain>> tickets = redisUtil.hashGet(key);//如果Redis有当前出发、到达地点和时间的数据 就直接缓存获取

            if (ObjectUtils.isEmpty(tickets)) {
                tickets = flightService.getTicketNew(domain);
                if (tickets.size() > 0) {
                    redisUtil.HashSet(key, tickets, 60 * 120);//过期时间单位是秒 可以在工具类的expire方法中设置TimeUNIT为MINUTES
                    result.setResult(tickets);
                }
            } else {
                result.setResult(tickets);
            }
            result.setTotal(tickets.size());
            return result;
        }
        return Result.error();
    }


    @GetMapping("/rec")
    public Result recommend(Long userId) {
        return recAlgorithmService.cityRecommend(userId);
    }
}
