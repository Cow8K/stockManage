package com.cow8k.stock.shop.controller;

import com.cow8k.stock.shop.entity.Stock;
import com.cow8k.stock.shop.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tiktokshop.open.sdk_java.invoke.ApiException;

import javax.annotation.Resource;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/14 17:36
 */


@RestController
public class StockController {

    @Resource
    private StockService stockService;

    @GetMapping("/test")
    public Stock test() {
        return stockService.getStockById(1);
    }


    @GetMapping("orders")
    public void order202309GetOrderListPost() throws Exception {

        System.out.println();
    }

    @GetMapping("/shop/cipher")
    public String getShopCipher() throws ApiException {

        return "123";
    }


}
