package com.zh.springo.pojo;

import lombok.Data;

/**
 * @description:
 * @author: zbxComputer
 * @time: 2022/6/21 15:34
 */
@Data
public class AgenciesDomain {
    private Integer id;
    private String agencies_code;
    private String agencies_name;

    @Override
    public String toString() {
        return "agencies{" +
                "id=" + id +
                ", agencies_code='" + agencies_code + '\'' +
                ", agencies_name='" + agencies_name + '\'' +
                '}';
    }
}
