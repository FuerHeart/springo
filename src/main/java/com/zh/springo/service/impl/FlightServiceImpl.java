package com.zh.springo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.springo.mapper.FlightMapper;
import com.zh.springo.pojo.FlightDomain;
import com.zh.springo.service.FlightService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zbxComputer
 * @time: 2022/7/4 16:58
 */
@Service
public class FlightServiceImpl extends ServiceImpl<FlightMapper, FlightDomain> implements FlightService {


    @Resource
    private FlightMapper flightMapper;

    /**
     * 得到直达的机票
     */
    @Override
    @Deprecated
    public List<FlightDomain> getTicket(FlightDomain fd) {
        if (Objects.isNull(fd)) {
            return null;
        }
        List<FlightDomain> ticket = flightMapper.getTicket(fd.getDeparture(), Set.of(fd.getArrival()), fd.getDepartureDatetime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
        ticket = ticket.stream().filter(e -> {
            LocalDateTime dept = LocalDateTime.parse(e.getDepartureDatetime(), formatter);
            LocalDateTime ar = LocalDateTime.parse(e.getArrivalDatetime(), formatter);
            e.setIsCrossDay(dept.getDayOfYear() != ar.getDayOfYear());
            e.setGrossTime(grossTime(dept, ar));
            e.setDepartureDatetime(formatter1.format(dept));
            e.setArrivalDatetime(formatter1.format(ar));
            return true;
        }).collect(Collectors.toList());
        return ticket;
    }

    @Override
    public Map<String, List<FlightDomain>> getTicketNew(FlightDomain flightDomain) {
        if (ObjectUtils.isEmpty(flightDomain)) {
            return null;
        }
        Map<String, List<FlightDomain>> result = new HashMap<>();
        String arrival = flightDomain.getArrival();
        String departure = flightDomain.getDeparture();
        String departureDate = flightDomain.getDepartureDate();
        //查询当天到目的地的航班
        List<FlightDomain> tickets = flightMapper.getTicket(null, Set.of(arrival), departureDate);
        //过滤出直达的 直接加入结果集
        List<FlightDomain> directlyArrival = tickets.stream().filter(e -> e.getDeparture().equals(departure)).collect(Collectors.toList());
        for (FlightDomain domain : directlyArrival) {
            result.put(domain.getFlightNo(), new ArrayList<>(List.of(domain)));
        }
//        result.put("directlyArrival", directlyArrival);
        //System.out.println(directlyArrival);
        //寻找当天除了当前出发城市外有哪些城市直接去往目的地
        Set<String> arrivals = tickets.stream().map(FlightDomain::getDeparture).collect(Collectors.toSet());
        arrivals = arrivals.stream().filter(e -> !e.equals(departure)).collect(Collectors.toSet());
        //System.out.println(collect);
        System.out.println(arrivals);
        //然后在寻找当天、当前城市到这些城市的航班
        List<FlightDomain> can = flightMapper.getTicket(departure, arrivals, departureDate);
        //System.out.println(can);
        //找到符合中转标准的航班
        //第二趟是tickets 第一趟是can
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss]]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();

        for (FlightDomain e : can) {
            for (FlightDomain ticket : tickets) {
                if (directlyArrival.contains(ticket)) {
                    continue;
                }
                if (!e.getArrival().equals(ticket.getDeparture())){
                    continue;
                }
                LocalDateTime d1a = LocalDateTime.parse(e.getArrivalDatetime(), dateTimeFormatter);
                LocalDateTime d2p = LocalDateTime.parse(ticket.getDepartureDatetime(), dateTimeFormatter);
                if (d2p.isAfter(d1a.plusHours(1))) {//预留最低一小时中转时间 第二趟航班要在第一趟航班到达后一小时起飞
                    LocalDateTime d2a = LocalDateTime.parse(ticket.getArrivalDatetime(), dateTimeFormatter);
                    LocalDateTime d1p = LocalDateTime.parse(e.getDepartureDatetime(), dateTimeFormatter);
                    e.setGrossTime(this.grossTime(d1p, d1a));
                    ticket.setGrossTime(this.grossTime(d2p, d2a));
                    List<FlightDomain> t = new ArrayList<>(List.of(e, ticket));
                    result.put(e.getFlightNo() + " " + ticket.getFlightNo(), t);
                }
            }
        }
        return result;
    }

    /**
     * 计算两个日期的时间差
     *
     * @param l1 LocalDateTime
     * @param l2 LocalDateTime
     * @return String
     */
    public String grossTime(LocalDateTime l1, LocalDateTime l2) {
        String res = "";
        Duration duration = Duration.between(l1, l2);

        if (duration.toHours() < 10) {
            res = "0" + duration.toHours() + "时";
        } else {
            res = duration.toHours() + "时";
        }
        long minutes = duration.toMinutes() - (duration.toHours() * 60);
        if (minutes < 10) {
            res += "0" + minutes + "分";
        } else {
            res += minutes + "分";
        }

        return res;
    }
}
