package com.sky.service.impl;


import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 营业额统计
     *
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的日期
        List<LocalDate> dateList = new ArrayList<>();
        if (begin != null && end != null) {
            while (!begin.isAfter(end)) {
                begin = begin.plusDays(1);
                dateList.add(begin);   //添加到日期集合中
            }
        }

        //创建集合,用于储存营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            //获取当前时间的最小值和最大值
            LocalDateTime minDate = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime maxDate = LocalDateTime.of(localDate, LocalTime.MAX);


//            //查询对应日期的营业额
//            String turnover =orderMapper.getTurnover(minDate,maxDate);

            //查询对应日期的营业额
            Map map = new HashMap<>();
            map.put("begin", minDate);
            map.put("end", maxDate);
            map.put("status", Orders.COMPLETED);
            Double turnover = orderMapper.getTurnover(map);
            //如果营业额为空则赋值为0
            turnover = turnover == null ? 0.0 : turnover;
            //将营业额封装在集合之中
            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 用户统计
     *
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的日期
        List<LocalDate> dateList = new ArrayList<>();
        if (begin != null && end != null) {
            while (!begin.isAfter(end)) {
                begin = begin.plusDays(1);
                dateList.add(begin);   //添加到日期集合中
            }
        }

        //创建集合,用于存放每天用户总量
        List<Integer> totalUserList = new ArrayList<>();

        //创建集合,用于存放每天新增用户
        List<Integer> newUserList = new ArrayList<>();
        for (LocalDate localDate : dateList) {
            //获取当前时间的最小值和最大值
            LocalDateTime minDate = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime maxDate = LocalDateTime.of(localDate, LocalTime.MAX);

            //查询总用户
            Map totalMap = new HashMap<>();
            totalMap.put("end", maxDate);
            Integer totalUser = userMapper.getUserReport(totalMap);
            totalUserList.add(totalUser);


            //查询新增用户
            Map newMap = new HashMap<>();
            newMap.put("begin", minDate);
            newMap.put("end", maxDate);
            Integer newUser = userMapper.getUserReport(newMap);
            newUserList.add(newUser);

        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    /**
     * 订单统计
     *
     * @param begin
     * @param end
     * @return
     */
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的日期
        List<LocalDate> dateList = new ArrayList<>();
        if (begin != null && end != null) {
            while (!begin.isAfter(end)) {
                begin = begin.plusDays(1);
                dateList.add(begin);   //添加到日期集合中
            }
        }

        //创建集合用来储存每日订单数
        List<Integer> orderCountList = new ArrayList<>();

        //创建集合用来存储每日有效订单数
        List<Integer> validOrderCountList = new ArrayList<>();


        for (LocalDate localDate : dateList) {
            //获取当前时间的最小值和最大值
            LocalDateTime minDate = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime maxDate = LocalDateTime.of(localDate, LocalTime.MAX);

            //获取每天订单数
            Integer orderCount = getOrderCount(minDate, maxDate, null);
            orderCountList.add(orderCount);

            //获取每日有效订单数
            Integer validordersCount = getOrderCount(minDate, maxDate, Orders.COMPLETED);
            validOrderCountList.add(validordersCount);

        }

        //订单总数
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        //有效订单数
        Integer validorderCount = validOrderCountList.stream().reduce(Integer::sum).get();

        //订单完成率
        double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = validorderCount.doubleValue() / totalOrderCount;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .validOrderCount(validorderCount)
                .totalOrderCount(totalOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 根据条件统计订单数量
     *
     * @param begin
     * @param end
     * @param status
     * @return
     */
    private Integer getOrderCount(LocalDateTime begin, LocalDateTime end, Integer status) {
        Map map= new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", status);
        return orderMapper.getOrderStatistics(map);
    }
}
