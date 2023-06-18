package com.zh.springo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.springo.config.Result;
import com.zh.springo.mapper.OrdersMapper;
import com.zh.springo.pojo.Orders;
import com.zh.springo.redisConfig.RedisUtil;
import com.zh.springo.service.OrdersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * (Orders)表服务实现类
 *
 * @author makejava
 * @since 2023-04-13 10:26:30
 */
@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Resource
    private OrdersMapper ordersMapper;

    @Override
    public Result getOrderByUserId(String userId) {
        List<Orders> orders = ordersMapper.getOrderByUserId(userId);
        if (ObjectUtils.isNotEmpty(orders)) {
            return new Result(200, orders);
        }
        return Result.error();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param orderno 主键
     * @return 实例对象
     */
    @Override
    public List<Map<String, Object>> queryById(BigInteger orderno, String userId) {
        return this.ordersMapper.queryById(orderno, userId);
    }

    /**
     * 分页查询
     *
     * @param orders      筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Orders> queryByPage(Orders orders, PageRequest pageRequest) {
        long total = this.ordersMapper.count(orders);
        return new PageImpl<>(this.ordersMapper.queryAllByLimit(orders, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    @Override
    public Orders insert(Orders orders) {
        this.ordersMapper.insert(orders);
        return orders;
    }

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    @Override
    public Map<String, Object> update(Orders orders) {
        this.ordersMapper.update(orders);
        return null;
    }

    /**
     * 通过主键删除数据
     *
     * @param orderno 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer orderno) {
        return this.ordersMapper.deleteById(orderno) > 0;
    }
}
