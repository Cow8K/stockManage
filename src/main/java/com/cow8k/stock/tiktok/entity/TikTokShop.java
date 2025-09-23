package com.cow8k.stock.tiktok.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/16 19:36
 */


@Data
public class TikTokShop {
    private Integer id;
    private String shopId;
    private String shopName;
    private String appKey;
    private String appSecret;
    private String autoCode;
    private String accessToken;
    private LocalDateTime accessTokenExpireTime;
    private String refreshToken;
    private LocalDateTime refreshTokenExpireTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
