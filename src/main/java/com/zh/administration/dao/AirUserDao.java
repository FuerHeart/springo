package com.zh.administration.dao;

import com.zh.administration.entity.AirUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (AirUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-29 14:09:36
 */
public interface AirUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AirUser queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param airUser  查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<AirUser> queryAllByLimit(AirUser airUser, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param airUser 查询条件
     * @return 总行数
     */
    long count(AirUser airUser);

    /**
     * 新增数据
     *
     * @param airUser 实例对象
     * @return 影响行数
     */
    int insert(AirUser airUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AirUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AirUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AirUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AirUser> entities);

    /**
     * 修改数据
     *
     * @param airUser 实例对象
     * @return 影响行数
     */
    int update(AirUser airUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

