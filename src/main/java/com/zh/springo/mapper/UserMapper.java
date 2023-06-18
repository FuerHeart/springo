package com.zh.springo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.springo.pojo.UserDomain;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDomain> {

    List<UserDomain> selectAll();

    List<UserDomain> getAllById(String id);

    UserDomain login(@Param("userName") String userName, @Param("passWord") String passWord);

    boolean userRegister(UserDomain userDomain);

    @Select("select count(1) from air_user where userName = #{userName})")
    int checkRepeatByName(String userName);

    @Select("select count(1) from air_user where phone = #{phone}")
    int checkRepeatByPhone(String phone);

}
