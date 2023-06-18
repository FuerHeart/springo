package com.zh.springo.controller;

import com.zh.springo.config.Result;
import com.zh.springo.pojo.UserDomain;
import com.zh.springo.redisConfig.RedisUtil;
import com.zh.springo.service.impl.UserServiceImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author ZhuBaiXuan
 * @Title: 用户操作类
 * @Package
 * @Description: 包含登录，注册等用户基本信息
 * @date 2022/4/28  23:56
 */

@RestController("UserController")
@RequestMapping("/users")
public class UserController {

    // 用户类日志
    public static final Logger logger = Logger.getLogger("userLogger");
    @Resource
    private UserServiceImpl userService;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 注册
     */
    @RequestMapping(value = "/userRegister", method = RequestMethod.POST)
    public Result userRegister(@RequestBody UserDomain users) {
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        String msg = userService.userRegister(users);
        result.setMessage(msg);
        if (!msg.equals("注册成功")) {
            result.setCode(Result.ERROR);
            result.setMessage(msg);
        }
        return result;
    }


    /**
     * 登录
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Result userLogin(@RequestBody UserDomain user) {
        Result result = new Result(Result.ERROR, "登录失败！");
        user = userService.login(user.getUserName(), user.getPassWord());
        if (!ObjectUtils.isEmpty(user)) {
            result.setCode(Result.SUCCESS);
            result.setMessage("登录成功！");
            byte[] encode = Base64.getEncoder().encode(String.valueOf(user.getUserId()).getBytes(StandardCharsets.UTF_8));
            String enctype = Base64.getEncoder().encodeToString(encode);
            Map<String, Object> map = new HashMap<>();
            redisUtil.set(enctype, enctype);
            map.put("token", enctype);
            map.put("user", user);
            result.setResult(map);
            result.total(map.size());
        }
        return result;
    }

    /**
     * 登出
     */
    @GetMapping("/userLogout")
    public Result userLogout(String userId) {
        Result result = new Result(Result.SUCCESS, "注销成功");
        redisUtil.del(userId);//清除redis中缓存
        return result;
    }

}
