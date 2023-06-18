package com.zh.springo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.springo.config.Result;
import com.zh.springo.mapper.CarouselMapper;
import com.zh.springo.pojo.Carousel;
import com.zh.springo.redisConfig.RedisUtil;
import com.zh.springo.service.CarouselService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Result selectALl() {

        List<Carousel> carousels = redisUtil.lGet("carousels", 0, -1);
        if (ObjectUtils.isNotEmpty(carousels)) {
            return new Result(200, carousels);
        } else {
            carousels = carouselMapper.selectALl();
            if (ObjectUtils.isNotEmpty(carousels)) {
                redisUtil.lSet("carousels", carousels, 3600);

                return new Result(200, carousels);

            }
        }
        return Result.error();
    }
}
