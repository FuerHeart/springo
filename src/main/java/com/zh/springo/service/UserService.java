package com.zh.springo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.springo.pojo.UserDomain;

import java.util.List;

public interface UserService extends IService<UserDomain> {

    List<UserDomain> selectAll();

    List<UserDomain> getAllById(String id);

    UserDomain login(String userName, String password);

    String userRegister(UserDomain userDomain);
}
