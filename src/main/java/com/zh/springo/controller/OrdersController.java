package com.zh.springo.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zh.springo.MQ.MsgProducer;
import com.zh.springo.config.Result;
import com.zh.springo.pojo.Orders;
import com.zh.springo.redisConfig.RedisUtil;
import com.zh.springo.service.OrdersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (Orders)表控制层
 *
 * @author makejava
 * @since 2023-04-13 10:26:28
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    /**
     * 服务对象
     */
    @Resource
    private OrdersService ordersService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MsgProducer msgProducer;

    /**
     * 分页查询
     *
     * @param orders      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Orders>> queryByPage(Orders orders, PageRequest pageRequest) {
        return ResponseEntity.ok(this.ordersService.queryByPage(orders, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/queryById")
    public Result queryById(BigInteger id, String userId) {
        System.out.println(userId);
        return new Result(200, "成功", this.ordersService.queryById(id, userId));
    }

    @GetMapping("/getOrder")
    public Result getOrderByUserId(String userId) {
        if (ObjectUtils.isEmpty(userId)) {
            return Result.error();
        }
        return ordersService.getOrderByUserId(userId);
    }

    /**
     * 新增数据
     *
     * @param orders 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Orders orders) {
        if (ObjectUtils.isEmpty(orders)) {
            return new Result(Result.ERROR, "数据不能为空");
        }
        //获取当前日期
        Timestamp curTimestamp = new Timestamp(System.currentTimeMillis());
        orders.setCreateTime(curTimestamp);
        //得到乘客json
        String passengers = orders.getPassengers();
        //得到每个乘客的身份证号码
        List<String> idCards = Stream.of(passengers.replaceAll("\\D+", ",").split(","))
                .filter(e -> e.length() == 18).collect(Collectors.toList());
        String redisKey = String.valueOf(orders.getFlightId());
        //根据身份证号码来判断该乘客是否已经购票
        //还可以再做一个返回已经买票了的乘客的username
        Set<String> objects = redisUtil.sGet(redisKey);
        //判断订单中的用户是否已经购买过这趟航班的票
        boolean flag = false;
        for (String idCard : idCards) {
            if (objects.contains(idCard)) {
                flag = true;
                break;
            }
        }
        //存储已经购票过的人的用户名
        List<String> usernames = new ArrayList<>();
        if (flag) {
            String s1 = passengers.substring(1, passengers.length() - 1);//去最外层大括号
            String[] split = s1.split(",");//以逗号分隔字符串
            //转为arrayList并翻转
            List<String> split1 = new ArrayList<>(List.of(split));
            Collections.reverse(split1);
            boolean f = false;
            //遍历判断并获取买过票的乘客的username
            for (String s2 : split1) {
                String[] t = s2.replaceAll("[{}'\"]", " ").strip().split(":");
                System.out.println(Arrays.toString(t));
                if (t[0].strip().equals("idCard")) {
                    f = objects.contains(t[1].strip());
                }
                if (t[0].strip().equals("username") && f) {
                    usernames.add(t[1].strip());
                }
            }
        }
        if (!flag) {

            orders = this.ordersService.insert(orders);

            //设置一个key 航班号 + 用户id + 航班起飞日期
            // String key = orders.getFlightId() + ":" + orders.getUserId();
            Map<String, Object> msg = new HashMap<>();
            msg.put("idCards", idCards);//这次订单的乘客身份证
            msg.put("orderNo", String.valueOf(orders.getOrderNo()));
            msg.put("redisKey", redisKey);
            msgProducer.sendDelayMsg("order", msg);
            //使用Redis Set集合 去存储 乘客的身份证号码
            for (String idCard : idCards) {
                redisUtil.sSetAndTime(redisKey, 1800, idCard);    // 这里是三十分钟
            }
            Map<String, Object> res = new HashMap<>();
            res.put("order", orders);
            return new Result(Result.SUCCESS, "提交订单成功", res);
        }
        return Result.error(usernames);
    }

    /**
     * 编辑数据
     *
     * @param orders 实体
     * @return 编辑结果
     */
    @PutMapping
    public Result edit(Orders orders) {
        this.ordersService.update(orders);
        return new Result();
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.ordersService.deleteById(id));
    }

}

