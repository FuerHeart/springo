package com.zh.administration.controller;

import com.zh.administration.entity.Airports;
import com.zh.administration.service.AirportsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Airports)表控制层
 *
 * @author makejava
 * @since 2022-10-29 14:15:10
 */
@RestController
@RequestMapping("airports")
public class AirportsController {
    /**
     * 服务对象
     */
    @Resource
    private AirportsService airportsService;

    /**
     * 分页查询
     *
     * @param airports 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Airports>> queryByPage(Airports airports, PageRequest pageRequest) {
        return ResponseEntity.ok(this.airportsService.queryByPage(airports, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Airports> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.airportsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param airports 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Airports> add(Airports airports) {
        return ResponseEntity.ok(this.airportsService.insert(airports));
    }

    /**
     * 编辑数据
     *
     * @param airports 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Airports> edit(Airports airports) {
        return ResponseEntity.ok(this.airportsService.update(airports));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.airportsService.deleteById(id));
    }

}

