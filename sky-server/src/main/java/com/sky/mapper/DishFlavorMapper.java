package com.sky.mapper;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    void insertBatch(List<DishFlavor> flavors);


    void deleteByDishID(List<Long> ids);




    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteByDishid(Long id);

    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getId(Long id);
}
