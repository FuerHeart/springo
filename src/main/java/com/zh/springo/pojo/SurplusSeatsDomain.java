package com.zh.springo.pojo;

import lombok.Data;

import java.math.BigInteger;

/**
 * @description:
 * @author: zbxComputer
 * @time: 2022/6/21 15:40
 */
@Data
public class SurplusSeatsDomain {

    private BigInteger id;
    private String carrier;
    private String flightNo;
    private String departure;
    private String arrival;
    private String departureDate;
    private Integer seatF;
    private Integer seatC;
    private Integer seatY;

    @Override
    public String toString() {
        return "SurplusSeatsDomain{" +
                "id=" + id +
                ", carrier='" + carrier + '\'' +
                ", flightNo='" + flightNo + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", seatF=" + seatF +
                ", seatC=" + seatC +
                ", seatY=" + seatY +
                '}';
    }
}
