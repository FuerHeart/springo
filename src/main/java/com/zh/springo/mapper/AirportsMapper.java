package com.zh.springo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.springo.pojo.AirportsDomain;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AirportsMapper extends BaseMapper<AirportsDomain> {

    List<AirportsDomain> selectAll();
    
}
