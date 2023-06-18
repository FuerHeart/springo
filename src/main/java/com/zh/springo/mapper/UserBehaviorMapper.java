package com.zh.springo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.springo.pojo.UserBehaviorDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserBehaviorMapper extends BaseMapper<UserBehaviorDomain> {

    @Select("select user_id,city_id,SUM(case frequency when frequency <= 3 then 1\n" +
            "when frequency <= 5 then 2.5\n" +
            "else 4 end) as `value` from user_behavior GROUP BY user_id,city_id ORDER BY user_id")
    List<UserBehaviorDomain> getALlBehaviors();
}
