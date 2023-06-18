package com.zh.springo.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhuBaiXuan
 * @Title:
 * @Package
 * @Description: 返回结果统一封装类
 * @date 2022/5/2  21:17
 */
@Data
public class Result implements Serializable {

    public static final int SUCCESS = 200;//成功
    public static final int ERROR = 500;//错误
    public static final int Warning = 600;//警告
    public static final int Other = 604;//其他
    private Integer code;

    private String message;

    private Object result;

    private Integer total;


    public Result() {
        super();
    }

    public Result result(Object result) {
        this.result = result;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result total(Integer total) {
        this.total = total;
        return this;
    }

    /**
     * 只返回状态，状态码，消息
     *
     * @param code
     * @param message
     */
    public Result(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * 只返回状态，状态码，数据对象
     *
     * @param code
     * @param result
     */
    public Result(Integer code, Object result) {
        super();
        this.code = code;
        this.result = result;
    }

    /**
     * 返回全部信息即状态，状态码，消息，数据对象
     *
     * @param code
     * @param message
     * @param result
     */
    public Result(Integer code, String message, Object result) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static Result error() {
        return new Result(Result.Other, "查询失败");
    }

    public static Result error(Object o) {
        return new Result(Result.Other, "查询失败", o);
    }

}
