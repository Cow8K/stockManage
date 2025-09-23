package com.cow8k.stock.tiktok.mapper;

import com.cow8k.stock.tiktok.entity.TikTokShop;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/16 19:35
 */

@Mapper
public interface TikTokShopMapper {
    Integer updateTikTokShopByShopId(TikTokShop tikTokShop);

    TikTokShop selectByShopId(String shopId);
}
