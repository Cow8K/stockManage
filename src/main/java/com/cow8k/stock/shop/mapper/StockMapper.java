package com.cow8k.stock.shop.mapper;

import com.cow8k.stock.shop.entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/14 15:58
 */

@Mapper
public interface StockMapper {
    @Select("select id, product_name, sku, stock_quantity, note from t_stock where id = #{id}")
    Stock getStockById(int id);
}
