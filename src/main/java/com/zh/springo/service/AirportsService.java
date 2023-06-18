package com.zh.springo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.springo.pojo.AirportsDomain;
import java.util.List;

public interface AirportsService extends IService<AirportsDomain> {

    List<AirportsDomain> selectAll();
}
