package com.sky.service.impl;


import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
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
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的日期
        List<LocalDate> dateList = new ArrayList<>();
        if(begin!= null && end!= null){
            while(!begin.isAfter(end)){
                begin = begin.plusDays(1);
                dateList.add(begin);   //添加到日期集合中
            }
        }

        //创建集合,用于储存营业额
        List<Double> turnoverList=new ArrayList<>();
        for (LocalDate localDate : dateList) {
            //获取当前时间的最小值和最大值
            LocalDateTime minDate=LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime maxDate=LocalDateTime.of(localDate, LocalTime.MAX);


//            //查询对应日期的营业额
//            String turnover =orderMapper.getTurnover(minDate,maxDate);

            //查询对应日期的营业额
            Map map=new HashMap<>();
            map.put("begin",minDate);
            map.put("end",maxDate);
            map.put("status", Orders.COMPLETED);
            Double turnover =orderMapper.getTurnover(map);
            //如果营业额为空则赋值为0
            turnover=turnover==null? 0.0 :turnover;
            //将营业额封装在集合之中
            turnoverList.add(turnover);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }

    /**
     * 用户统计
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        //当前集合用于存放从begin到end范围内的日期
        List<LocalDate> dateList = new ArrayList<>();
        if(begin!= null && end!= null){
            while(!begin.isAfter(end)){
                begin = begin.plusDays(1);
                dateList.add(begin);   //添加到日期集合中
            }
        }

        //创建集合,用于存放每天用户总量
        List<Integer> totalUserList=new ArrayList<>();

        //创建集合,用于存放每天新增用户
        List<Integer> newUserList=new ArrayList<>();
        for (LocalDate localDate : dateList) {
            //获取当前时间的最小值和最大值
            LocalDateTime minDate=LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime maxDate=LocalDateTime.of(localDate, LocalTime.MAX);

            //查询总用户
            Map totalMap=new HashMap<>();
            totalMap.put("end",maxDate);
            Integer totalUser=userMapper.getUserReport(totalMap);
            totalUserList.add(totalUser);


            //查询新增用户
            Map newMap=new HashMap<>();
            newMap.put("begin",minDate);
            newMap.put("end",maxDate);
            Integer newUser=userMapper.getUserReport(newMap);
            newUserList.add(newUser);

        }
        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList,","))
                .totalUserList(StringUtils.join(totalUserList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .build();
    }
}
