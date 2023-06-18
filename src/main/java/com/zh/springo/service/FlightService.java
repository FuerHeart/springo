package com.zh.springo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.springo.pojo.FlightDomain;

import java.util.List;
import java.util.Map;

public interface FlightService extends IService<FlightDomain> {

    List<FlightDomain> getTicket(FlightDomain flightDomain);

    Map<String, List<FlightDomain>> getTicketNew(FlightDomain flightDomain);
}
