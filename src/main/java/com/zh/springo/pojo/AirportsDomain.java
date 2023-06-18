package com.zh.springo.pojo;

import lombok.Data;

@Data
public class AirportsDomain {
    private Integer id;
    private String airports_code;
    private String airports_name;
    private String airports_locate;

    private String airports_area;

    @Override
    public String toString() {
        return "AirportsDomain{" +
                "id=" + id +
                ", airports_code='" + airports_code + '\'' +
                ", airports_name='" + airports_name + '\'' +
                ", airports_locate='" + airports_locate + '\'' +
                '}';
    }
}
