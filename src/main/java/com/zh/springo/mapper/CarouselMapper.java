package com.zh.springo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zh.springo.pojo.Carousel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CarouselMapper extends BaseMapper<Carousel> {

    @Select("select * from carousel")
    List<Carousel> selectALl();
}
