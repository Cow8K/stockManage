package com.cow8k.stock.shop.service;

import com.cow8k.stock.shop.entity.Stock;
import com.cow8k.stock.shop.mapper.StockMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/14 17:39
 */


@Service
public class StockService {
    @Resource
    private StockMapper stockMapper;

    public Stock getStockById(int id) {
        return stockMapper.getStockById(id);
    }
}
