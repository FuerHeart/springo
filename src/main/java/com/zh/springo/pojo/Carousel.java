package com.zh.springo.pojo;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class Carousel {
    private Long id;
    private String departure;
    private String departureCode;
    private String arrival;
    private String arrivalCode;
    private Date departureDatetime;
    private Double price;
    private String imgUrl;
}
