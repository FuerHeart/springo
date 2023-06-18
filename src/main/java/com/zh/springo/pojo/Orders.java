package com.zh.springo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * (Orders)实体类
 *
 * @author makejava
 * @since 2023-04-13 10:26:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Orders extends Model<Orders> implements Serializable{
    /**
     * 序号 雪花id auto就是自增 默认就提供五个方法 none input uuid 雪花id 自增
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long orderNo;
    /**
     * 乘机人信息
     */
    private String passengers;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 支付金额
     */
    private Double amountPaid;
    /**
     * 航班ID
     */
    private Integer flightId;
    /**
     * 下单账号ID
     */
    private String userId;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    private Timestamp createTime;

}

