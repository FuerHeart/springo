package com.zh.springo.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.springo.mapper.AirportsMapper;
import com.zh.springo.pojo.AirportsDomain;
import com.zh.springo.redisConfig.RedisUtil;
import com.zh.springo.service.AirportsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AirportsServiceImpl extends ServiceImpl<AirportsMapper, AirportsDomain> implements AirportsService {

    @Resource
    private AirportsMapper airportsMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @param []
     * @description: 查询并返回所有机场
     * @return: java.util.List<com.zzc.air_system.model.AirportsDomain>
     * @author: zbxComputer
     * @datetime: 2022/6/17 13:36
     */
    @Override
    public List<AirportsDomain> selectAll() {
        List<AirportsDomain> list;
        if (!redisUtil.hasKey("allAirports")) {
            list = airportsMapper.selectAll();
            redisUtil.lSet("allAirports", list, 600);
            return list;
        }
        list = redisUtil.lGet("allAirports", 0, -1);
        return list;
    }


}
