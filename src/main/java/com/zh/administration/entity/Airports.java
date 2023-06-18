package com.zh.administration.entity;

import java.io.Serializable;

/**
 * (Airports)实体类
 *
 * @author makejava
 * @since 2022-10-29 14:15:10
 */
public class Airports implements Serializable {
    private static final long serialVersionUID = -72905329031098612L;
    
    private Integer id;
    /**
     * 机场编号
     */
    private String airportsCode;
    /**
     * 机场名称
     */
    private String airportsName;
    /**
     * 所在城市
     */
    private String airportsLocate;
    /**
     * 所在地区
     */
    private String airportsArea;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirportsCode() {
        return airportsCode;
    }

    public void setAirportsCode(String airportsCode) {
        this.airportsCode = airportsCode;
    }

    public String getAirportsName() {
        return airportsName;
    }

    public void setAirportsName(String airportsName) {
        this.airportsName = airportsName;
    }

    public String getAirportsLocate() {
        return airportsLocate;
    }

    public void setAirportsLocate(String airportsLocate) {
        this.airportsLocate = airportsLocate;
    }

    public String getAirportsArea() {
        return airportsArea;
    }

    public void setAirportsArea(String airportsArea) {
        this.airportsArea = airportsArea;
    }

}

