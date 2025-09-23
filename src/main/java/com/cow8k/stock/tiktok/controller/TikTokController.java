package com.cow8k.stock.tiktok.controller;

import com.cow8k.stock.tiktok.entity.TikTokShop;
import com.cow8k.stock.tiktok.service.TikTokService;
import com.cow8k.stock.tiktok.utils.AccessTokenUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/17 21:22
 */


@RestController
public class TikTokController {
    @Resource
    private TikTokService tikTokService;

    @Resource
    private AccessTokenUtil accessTokenUtil;

    @GetMapping("/updateShop")
    public String updateShop() {
        TikTokShop tikTokShop = new TikTokShop();
        tikTokShop.setShopId("Test Shop Id");
        tikTokShop.setShopName("Test Shop Name2");
        tikTokShop.setAppKey("Test App Key2");

        tikTokService.updateTikTokShopByShopId(tikTokShop);
        return "123";
    }

    @GetMapping("/selectShop")
    public TikTokShop selectShop() {
        try {
            accessTokenUtil.getAccessToken("1");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tikTokService.getTikTokShopByShopId("1");
    }
}
