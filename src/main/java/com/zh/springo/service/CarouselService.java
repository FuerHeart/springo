package com.zh.springo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.springo.config.Result;
import com.zh.springo.pojo.Carousel;

public interface CarouselService extends IService<Carousel> {

    Result selectALl();

}
