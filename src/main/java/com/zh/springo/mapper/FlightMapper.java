package com.zh.springo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.springo.pojo.FlightDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface FlightMapper extends BaseMapper<FlightDomain> {
    List<FlightDomain> getTicket(@Param("departure") String departure, @Param("arrivals") Set<String> arrival, @Param("departureDate") String departureDate);

    List<Map<Long, Object>> getCities(List<Long> ids);

}
