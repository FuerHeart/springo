package com.zh.administration.service.impl;

import com.zh.administration.entity.Airports;
import com.zh.administration.dao.AirportsDao;
import com.zh.administration.service.AirportsService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Airports)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 14:15:16
 */
@Service("airportsService")
public class AirportsServiceImpl implements AirportsService {
    @Resource
    private AirportsDao airportsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Airports queryById(Integer id) {
        return this.airportsDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param airports 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Airports> queryByPage(Airports airports, PageRequest pageRequest) {
        long total = this.airportsDao.count(airports);
        return new PageImpl<>(this.airportsDao.queryAllByLimit(airports, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param airports 实例对象
     * @return 实例对象
     */
    @Override
    public Airports insert(Airports airports) {
        this.airportsDao.insert(airports);
        return airports;
    }

    /**
     * 修改数据
     *
     * @param airports 实例对象
     * @return 实例对象
     */
    @Override
    public Airports update(Airports airports) {
        this.airportsDao.update(airports);
        return this.queryById(airports.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.airportsDao.deleteById(id) > 0;
    }
}
