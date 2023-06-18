package com.zh.springo;

import com.zh.springo.mapper.FlightMapper;
import com.zh.springo.pojo.FlightDomain;
import io.lettuce.core.ScriptOutputType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class SpingoApplicationTests {

    @Resource
    private FlightMapper flightMapper;

    @Test
    void contextLoads() {
        String s = "[{'username': '曾8曾', 'idCard': '450924200102124937'}, {'username': '曾77曾', 'idCard': '450924200102124928'}]";
        String s1 = s.substring(1, s.length() - 1);
        String[] split = s1.split(",");
        List<String> split1 = new ArrayList<>(List.of(split));
        Collections.reverse(split1);
        List<String> usernames = new ArrayList<>();
        for (String s2 : split1) {
            String t = s2.replaceAll("[{}']", " ").strip();
            System.out.println(t);
            if (t.split(":")[0].strip().equals("username")) {
                usernames.add(t.split(":")[1].strip());
                System.out.println(t.split(":")[1].strip());
            }
        }
        System.out.println(usernames);
    }

    @Test
    void demo(){
        System.out.println(String.valueOf(new Timestamp(System.currentTimeMillis())));
    }


    @Test
    void transfer() {
        //查询当天到目的地的航班
        List<FlightDomain> tickets = flightMapper.getTicket(null, Set.of("TSN"), "2023-05-07");
        //过滤出直达的
        List<FlightDomain> directlyArrival = tickets.stream().filter(e -> {
            return e.getDeparture().equals("CAN");
        }).collect(Collectors.toList());
        System.out.println(directlyArrival);
        //寻找当天有哪些城市去往目的地
        Set<String> collect = tickets.stream().map(FlightDomain::getDeparture).collect(Collectors.toSet());
        System.out.println(collect);
        //然后在寻找当天、当前城市到这些城市的航班
        List<FlightDomain> can = flightMapper.getTicket("CAN", collect, "2023-05-07");
        //System.out.println(can);
        //找到符合中转标准的航班
        //第二趟是tickets 第一趟是can
        Map<String, List<FlightDomain>> result = new HashMap<>();

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd[ [HH][:mm][:ss]]")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        for (FlightDomain e : can) {
            for (FlightDomain ticket : tickets) {
                LocalTime d1 = LocalTime.parse(e.getArrivalDatetime(), dateTimeFormatter);
                LocalTime d2 = LocalTime.parse(ticket.getDepartureDatetime(), dateTimeFormatter);
                if (d2.isAfter(d1)) {
                    List<FlightDomain> t = new ArrayList<>(List.of(e, ticket));
                    result.put(e.getFlightNo() + " " + ticket.getFlightNo(), t);
                    /*System.out.println(e.getFlightNo() + " " + ticket.getFlightNo());
                    System.out.println(e.getArrivalDatetime() + " " + ticket.getDepartureDatetime() + e.getDeparture() + e.getArrival());*/
                }
            }
        }
        //System.out.println(result.values());
    }

}
