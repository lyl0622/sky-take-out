package com.sky.controller.user;


import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "购物车相关接口")
@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    /**
     * 添加购物车
     * @param shoppingCartDTO
     * @return
     */
    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result addShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车：{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查看购物车
     * @return
     */
    @ApiOperation("查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> showShoppingCart(){
        List list=shoppingCartService.showShoppingCart();
        return Result.success(list);
    }

    /**
     * 删除购物车
     * @param ShoppingCartDTO
     * @return
     */
    @ApiOperation("删除购物车")
    @PostMapping("/sub")
    public Result deleteShoppingcart(@RequestBody ShoppingCartDTO ShoppingCartDTO){
        log.info("删除购物车：{}",ShoppingCartDTO);
        shoppingCartService.deleteShoppingCart(ShoppingCartDTO);
        return Result.success();
    }

    /**
     * 清空购物车
     * @return
     */
    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result cleanShoppingCart(){
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //删除当前用户购物车
        shoppingCartService.cleanShoppingCart(userId);
        return Result.success();
    }
}
