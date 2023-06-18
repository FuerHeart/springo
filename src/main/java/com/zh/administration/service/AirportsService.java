package com.zh.administration.service;

import com.zh.administration.entity.Airports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Airports)表服务接口
 *
 * @author makejava
 * @since 2022-10-29 14:15:16
 */
public interface AirportsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Airports queryById(Integer id);

    /**
     * 分页查询
     *
     * @param airports 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Airports> queryByPage(Airports airports, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param airports 实例对象
     * @return 实例对象
     */
    Airports insert(Airports airports);

    /**
     * 修改数据
     *
     * @param airports 实例对象
     * @return 实例对象
     */
    Airports update(Airports airports);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
