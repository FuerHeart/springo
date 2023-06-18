package com.zh.springo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBehaviorDomain {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "city_id")
    private Integer cityId;       //城市编号
    private Integer frequency;  //搜索次数
    @TableField(exist = false)
    private Integer value;      //这个字段在数据表中不需要存在 用来存放用户评分的

}
