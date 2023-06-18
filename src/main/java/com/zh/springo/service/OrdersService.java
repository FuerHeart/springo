package com.zh.springo.service;


import com.zh.springo.config.Result;
import com.zh.springo.pojo.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * (Orders)表服务接口
 *
 * @author makejava
 * @since 2023-04-13 10:26:30
 */
public interface OrdersService {



    Result getOrderByUserId(String userId);


    /**
     * 通过ID查询单条数据
     *
     * @param orderno 主键
     * @return 实例对象
     */
    List<Map<String, Object>> queryById(BigInteger orderno, String userId);

    /**
     * 分页查询
     *
     * @param orders      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Orders> queryByPage(Orders orders, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    Orders insert(Orders orders);

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    Map<String, Object> update(Orders orders);

    /**
     * 通过主键删除数据
     *
     * @param orderno 主键
     * @return 是否成功
     */
    boolean deleteById(Integer orderno);

}
