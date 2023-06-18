package com.zh.administration.controller;

import com.zh.administration.entity.AirUser;
import com.zh.administration.service.AirUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * (AirUser)表控制层
 *
 * @author makejava
 * @since 2022-10-29 14:09:34
 */
@RestController
@RequestMapping("airUser")
public class AirUserController {
    /**
     * 服务对象
     */
    @Resource
    private AirUserService airUserService;

    /**
     * 分页查询
     *
     * @param airUser     筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<AirUser>> queryByPage(AirUser airUser, PageRequest pageRequest) {
        if (Objects.isNull(airUser)){
            throw new RuntimeException("对象为空");
        }
        return ResponseEntity.ok(this.airUserService.queryByPage(airUser, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping
    public ResponseEntity<AirUser> queryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.airUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param airUser 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<AirUser> add(AirUser airUser) {
        return ResponseEntity.ok(this.airUserService.insert(airUser));
    }

    /**
     * 编辑数据
     *
     * @param airUser 实体
     * @return 编辑结果
     */
    @PutMapping("/user/edit/{id]")
    public ResponseEntity<AirUser> edit(AirUser airUser) {
        return ResponseEntity.ok(this.airUserService.update(airUser));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(String id) {
        return ResponseEntity.ok(this.airUserService.deleteById(id));
    }

}

