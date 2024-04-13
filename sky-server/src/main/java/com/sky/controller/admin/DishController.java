package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Api(tags = "菜品相关接口")
@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("新增菜品")
    @PostMapping()
    public Result<String> save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        //将菜品缓存清理掉，所有dish_开头的
        String key="dish_"+dishDTO.getId();
        CleanCache(key);
        return Result.success();
    }

    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> pageQuery( DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询：{}",dishPageQueryDTO);
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    @ApiOperation("批量删除菜品")
    @DeleteMapping()
    public Result delete(@RequestParam List<Long> ids){
        log.info("批量删除菜品：{}",ids);
        dishService.delete(ids);
        //将所有的菜品缓存清理掉，所有dish_开头的
        CleanCache("dish_*");
        return Result.success();
    }

    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishDTO> getId(@PathVariable Long id){
        log.info("根据id查询菜品 id：{}",id);
        DishDTO dishDTO= dishService.getId(id);
        return Result.success(dishDTO);
    }

    @ApiOperation("修改菜品")
    @PutMapping()
    public Result<String> DishUpdate(@RequestBody DishDTO dishDTO){
        log.info("修改菜品：{}",dishDTO);
        dishService.dishUpdate(dishDTO);
        //将所有的菜品缓存清理掉，所有dish_开头的
        CleanCache("dish_*");
        return Result.success();
    }

    @ApiOperation("菜品停售起售")
    @PostMapping("/status/{status}")
    public Result<String> StatusDish(@PathVariable Integer status,Long id){
        log.info("菜品停售起售:{},{}",status,id);
        dishService.StatusDish(status,id);
        //将所有的菜品缓存清理掉，所有dish_开头的
        CleanCache("dish_*");

        return Result.success();
    }

    /**
     * 统一删除redis
     * @param pattern
     */
    private void CleanCache(String pattern){
        Set key=redisTemplate.keys(pattern);
        redisTemplate.delete(key);
    }
}
