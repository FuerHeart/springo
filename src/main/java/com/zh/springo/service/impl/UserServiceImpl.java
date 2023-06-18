package com.zh.springo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.springo.mapper.UserMapper;
import com.zh.springo.pojo.UserDomain;
import com.zh.springo.redisConfig.RedisUtil;
import com.zh.springo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDomain> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<UserDomain> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public List<UserDomain> getAllById(String id) {
        return userMapper.getAllById(id);
    }

    @Override
    public UserDomain login(String userName, String password) {
        UserDomain user = userMapper.login(userName, password);
        if (!ObjectUtils.isEmpty(user) && ObjectUtils.isEmpty(redisUtil.get(String.valueOf(user.getUserId())))) {
            redisUtil.set(String.valueOf(user.getUserId()), user);
        }
        return user;
    }

    @Override
    public String userRegister(UserDomain userDomain) {
        String result = "注册失败！";
        if (!ObjectUtils.isEmpty(userDomain)) {
            if (StringUtils.hasLength(userDomain.getUserName()) && StringUtils.hasLength(userDomain.getPassWord())
                    && StringUtils.hasLength(userDomain.getPhone()) && StringUtils.hasLength(userDomain.getGender())) {
                try {
                    if (userMapper.userRegister(userDomain))
                        result = "注册成功";
                } catch (Exception e) {
                    result = e.getCause().getMessage();
                    Logger.getLogger("").warning("注册失败：" + e.getCause().getMessage());
                }
            }
        }
        return result;
    }
}
