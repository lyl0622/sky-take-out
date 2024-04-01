package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品ID，来查询相应套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealidsByDsihIds(List<Long> dishIds);
}
