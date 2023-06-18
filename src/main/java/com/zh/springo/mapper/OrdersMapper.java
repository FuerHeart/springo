package com.zh.springo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.springo.pojo.Orders;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * (Orders)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-13 10:26:29
 */
public interface OrdersMapper extends BaseMapper<Orders> {


    @Select("select * from orders where userId = #{userId}")
    List<Orders> getOrderByUserId(String userId);


    //查询订单状态
    @Select("select status from orders where orderNo = #{orderNo}")
    int retrieveOrderStatus(Long orderNo);

    //

    /**
     * 通过ID查询单条数据
     *
     * @param orderNo 主键
     * @return 实例对象
     */
    List<Map<String, Object>> queryById(@Param("orderNo") BigInteger orderNo, @Param("userId") String userId);

    /**
     * 查询指定行数据
     *
     * @param orders   查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Orders> queryAllByLimit(Orders orders, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param orders 查询条件
     * @return 总行数
     */
    long count(Orders orders);

    /**
     * 新增数据
     *
     * @param orders 实例对象
     * @return 影响行数
     */
    int insert(Orders orders);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Orders> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Orders> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Orders> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Orders> entities);

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 影响行数
     */
    int update(Orders orders);

    /**
     * 通过主键删除数据
     *
     * @param orderno 主键
     * @return 影响行数
     */
    int deleteById(Long orderno);

}

