package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface OrderMapper {

    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 分页查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<OrderVO> page(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查找订单
     * @param id
     * @return
     */
    @Select("select *from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 查找订单数量
     * @param toBeConfirmed
     * @return
     */
    @Select("select count(*) from orders where status=#{toBeConfirmed}")
    Integer countStatus(Integer toBeConfirmed);
}
