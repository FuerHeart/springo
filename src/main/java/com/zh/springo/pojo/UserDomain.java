package com.zh.springo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author ZhuBaiXuan
 * @Title: 用户实体类
 * @Package 模型
 * @Description:
 * @date 2022/4/1312:40
 */
@Data
public class UserDomain {

    @TableId(value = "userId", type = IdType.ASSIGN_ID)
    private Long userId;
    private String phone;
    private String email;
    private String userName;
    private String passWord;
    private String realName;
    private String gender;
    private String portrait;
    private String idCard;

}
