package com.cow8k.stock.tiktok.service;

import com.cow8k.stock.tiktok.entity.TikTokShop;
import com.cow8k.stock.tiktok.mapper.TikTokShopMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/17 21:14
 */


@Service
public class TikTokService {
    @Resource
    private TikTokShopMapper tikTokShopMapper;

    public Integer updateTikTokShopByShopId(TikTokShop tikTokShop) {
        return tikTokShopMapper.updateTikTokShopByShopId(tikTokShop);
    }

    public TikTokShop getTikTokShopByShopId(String shopId) {
        return tikTokShopMapper.selectByShopId(shopId);
    }
}
