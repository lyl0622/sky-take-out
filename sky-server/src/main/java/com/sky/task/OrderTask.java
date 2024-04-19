package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j

public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 超时订单处理方法
     */
    @Scheduled(cron = "0 * * * * ? ")//每分钟触发一次
    public void processTimeoutOrder() {
        log.info("定时处理超时订单:{}", LocalDateTime.now());
        Integer status = Orders.PENDING_PAYMENT;
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);

        //查询订单表中的超时订单
        List<Orders> list = orderMapper.getByStatusAndOrderTime(status, time);

        if (list != null && list.size() > 0) {
            //遍历超时订单，修改订单状态为超时
            for (Orders order : list) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时未支付，系统自动取消订单");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
                log.info("订单:{}超时，已取消", order.getId());
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("定时处理还在派送中订单:{}", LocalDateTime.now());
        Integer status = Orders.DELIVERY_IN_PROGRESS;
        LocalDateTime time = LocalDateTime.now();

        //查询订单表中的超时订单
        List<Orders> list = orderMapper.getByStatusAndOrderTime(status, time);

        if (list != null && list.size() > 0) {
            //遍历超时订单，修改订单状态为超时
            for (Orders order : list) {
                order.setStatus(Orders.COMPLETED); //修改订单状态为已完成
                orderMapper.update(order);
            }
        }
    }
}
