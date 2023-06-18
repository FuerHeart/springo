package com.zh.administration.service;

import com.zh.administration.entity.AirUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (AirUser)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 14:09:40
 */
public interface AirUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AirUser queryById(String id);

    /**
     * 分页查询
     *
     * @param airUser     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<AirUser> queryByPage(AirUser airUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param airUser 实例对象
     * @return 实例对象
     */
    AirUser insert(AirUser airUser);

    /**
     * 修改数据
     *
     * @param airUser 实例对象
     * @return 实例对象
     */
    AirUser update(AirUser airUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
