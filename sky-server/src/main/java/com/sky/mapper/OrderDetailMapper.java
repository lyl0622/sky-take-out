package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insertBatch(ArrayList<OrderDetail> orderDetailArrayList);

    @Select("select * from order_detail where id=#{id}")
    List<OrderDetail> getByOrderId(Long id);
}
