package com.zh.administration.service.impl;

import com.zh.administration.entity.AirUser;
import com.zh.administration.dao.AirUserDao;
import com.zh.administration.service.AirUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (AirUser)表服务实现类
 *
 * @author makejava
 * @since 2022-10-29 14:09:41
 */
@Service("airUserService")
public class AirUserServiceImpl implements AirUserService {
    @Resource
    private AirUserDao airUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AirUser queryById(String id) {
        return this.airUserDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param airUser     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<AirUser> queryByPage(AirUser airUser, PageRequest pageRequest) {
        long total = this.airUserDao.count(airUser);
        return new PageImpl<>(this.airUserDao.queryAllByLimit(airUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param airUser 实例对象
     * @return 实例对象
     */
    @Override
    public AirUser insert(AirUser airUser) {
        this.airUserDao.insert(airUser);
        return airUser;
    }

    /**
     * 修改数据
     *
     * @param airUser 实例对象
     * @return 实例对象
     */
    @Override
    public AirUser update(AirUser airUser) {
        this.airUserDao.update(airUser);
        return this.queryById(airUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.airUserDao.deleteById(id) > 0;
    }
}
