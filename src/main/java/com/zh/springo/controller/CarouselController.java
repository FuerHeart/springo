package com.zh.springo.controller;

import com.zh.springo.config.Result;
import com.zh.springo.service.CarouselService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/carousel")
@RestController
public class CarouselController {

    @Resource
    private CarouselService carouselService;

    @GetMapping("/all")
    public Result selectAll() {
        return carouselService.selectALl();
    }
}
