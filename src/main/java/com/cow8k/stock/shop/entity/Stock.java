package com.cow8k.stock.shop.entity;

import lombok.Data;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/14 17:21
 */


@Data
public class Stock {
    private Integer id;
    private String productName;
    private String sku;
    private Integer stockQuantity;
    private String note;
}
