package com.zh.springo.pojo;

import lombok.Data;

import java.math.BigInteger;

/**
 * @description:
 * @author: Computer
 * @time: 2022/6/21 15:36
 */
@Data
public class FlightDomain {
    private BigInteger flightId;
    private String carrier;
    private String flightNo;
    private String departure;
    private String departureCityName;
    private String departureAirportCode;
    private String departureAirportName;
    private String departureTerminal;
    private String departureDate;
    private String departureDatetime;
    private String arrival;
    private String arrivalCityName;
    private String arrivalAirportCode;
    private String arrivalAirportName;
    private String arrivalTerminal;
    private String arrivalDate;
    private String arrivalDatetime;
    private String grossTime;
    private Boolean isCrossDay;
    private Integer airportTax;
    private Integer fuelTax;
    private Integer priceFare;
    private String planeStyle;
    private String planeSize;   //最简单的就是直接在实体类里面加这个字段就行 大小写严格一致
    private String punctualityRate;

    private String airlineName;
    private String airlineLogo;

    private String seatC;
    private String seatF;
    private String seatY;
    private String cabin;
    private String amount;

    private String sequenceNo;
    private String nextCarrier;
    private String surcharge;
}
