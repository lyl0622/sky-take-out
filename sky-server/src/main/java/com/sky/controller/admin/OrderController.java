package com.sky.controller.admin;


import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "订单管理相关接口")
@RestController
@RequestMapping("/admin/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/conditionSearch")
    public Result<PageResult> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("订单分页查询:{}",ordersPageQueryDTO);
         PageResult pageResult=orderService.PageQuery(ordersPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 取消订单
     * @param ordersCancelDTO
     * @return
     */
    @ApiOperation("订单取消")
    @PutMapping("/cancel")
    public Result Cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
        log.info("取消订单：{}",ordersCancelDTO);
        orderService.Cancel(ordersCancelDTO);
        return Result.success();
    }

    /**
     * 拒绝订单
     * @param ordersRejectionDTO
     * @return
     */
    @ApiOperation("拒绝订单")
    @PutMapping("/rejection")
    public Result Rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        log.info("拒绝订单：{}",ordersRejectionDTO);
        orderService.Rejection(ordersRejectionDTO);
        return Result.success();
    }


    /**
     * 接单
     * @param ordersConfirmDTO
     * @return
     */
    @ApiOperation("接单")
    @PutMapping("/confirm")
    public Result Confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("接单:{}",ordersConfirmDTO);
        orderService.Confirm(ordersConfirmDTO);
        return Result.success();
    }


    /**
     * 派送订单
     *
     * @return
     */
    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result delivery(@PathVariable("id") Long id) {
        orderService.delivery(id);
        return Result.success();
    }

    /**
     * 完成订单
     *
     * @return
     */
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable("id") Long id) {
        orderService.complete(id);
        return Result.success();
    }

    /**
     * 各个状态的订单数量统计
     * @return
     */
    @ApiOperation("各个状态的订单数量统计")
    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> Statis(){
        OrderStatisticsVO orderStatisticsVO=orderService.getStatis();
        return Result.success(orderStatisticsVO);
    }


    @ApiOperation("查询订单详情")
    @GetMapping("/details/{id}")
    public Result<OrderVO> Details(@PathVariable("id") Long id){
        log.info("查询订单详情:{}",id);
        OrderVO orderVO=orderService.getDetails(id);
        return Result.success(orderVO);
    }


}
