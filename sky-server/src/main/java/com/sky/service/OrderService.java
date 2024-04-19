package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     */
    PageResult PageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 取消顶单
     * @param ordersCancelDTO
     */
    void Cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * 拒绝订单
     * @param ordersRejectionDTO
     */
    void Rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 各个状态的订单数量统计
     * @return
     */
    OrderStatisticsVO getStatis();

    /**
     * 接单
     * @param ordersConfirmDTO
     */
    void Confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 派送订单
     * @param id
     */
    void delivery(Long id);

    /**
     * 完成订单
     * @param id
     */
    void complete(Long id);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    OrderVO getDetails(Long id);
}
