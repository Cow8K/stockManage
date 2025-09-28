package com.cow8k.stock.tiktok.utils;

import com.cow8k.stock.common.CommonUtil;
import com.cow8k.stock.tiktok.entity.TikTokShop;
import com.cow8k.stock.tiktok.mapper.TikTokShopMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/16 19:26
 */

@Component
public class AccessTokenUtil {
    @Resource
    private TikTokShopMapper tikTokShopMapper;

    public String getAccessToken(String shopId) throws URISyntaxException, IOException {
        TikTokShop tikTokShop = tikTokShopMapper.selectByShopId(shopId);

        if (tikTokShop == null) {
            throw new RuntimeException("未查询到店铺！");
        }

        if (StringUtils.isBlank(tikTokShop.getAccessToken())) {
            /*
            * auth_code 有效期只有半小时
            * access_token 为空的基本上要手动获取
            * getAccessToken(TikTokShop tikTokShop) 暂时保留
            * */
            return this.getAccessToken(tikTokShop);
        }

        LocalDateTime accessTokenExpireTime = tikTokShop.getAccessTokenExpireTime();
        if (LocalDateTime.now().isBefore(accessTokenExpireTime)) {
            return tikTokShop.getAccessToken();
        }

        // access_token 已经过期
        String accessToken = this.refreshAccessToken(tikTokShop);
        tikTokShop.setAccessToken(accessToken);

        return accessToken;
    }

    public String refreshAccessToken(TikTokShop tikTokShop) throws URISyntaxException, IOException {
        String path = "/refresh";
        Map<String, String> params = new HashMap<>();

        params.put("grant_type", "refresh_token");
        params.put("app_key", tikTokShop.getAppKey());
        params.put("app_secret", tikTokShop.getAppSecret());
        params.put("refresh_token", tikTokShop.getRefreshToken());

        String requestPath = this.getRequestPath(path, params);
        this.updateShopInfo(tikTokShop, requestPath);

        return tikTokShop.getAccessToken();
    }

    private String getAccessToken(TikTokShop tikTokShop) throws URISyntaxException, IOException  {
        String path = "/get";

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorized_code");
        params.put("app_key", tikTokShop.getAppKey());
        params.put("app_secret", tikTokShop.getAppSecret());
        params.put("auth_code", tikTokShop.getAuthCode());

        String requestPath = this.getRequestPath(path, params);
        this.updateShopInfo(tikTokShop, requestPath);

        return tikTokShop.getAccessToken();
    }

    private String getRequestPath(String path, Map<String, String> params) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https")
                .setHost("auth.tiktok-shops.com/api/v2/token")
                .setPath(path);

        // 拼接请求参数
        params.forEach(uriBuilder::addParameter);

        return uriBuilder.build().toString();
    }

    private void updateShopInfo(TikTokShop tikTokShop, String requestPath) throws IOException {
        HttpGet httpget = new HttpGet(requestPath);

        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpget))
        {
            int statusCode = response.getStatusLine().getStatusCode();

            // 请求失败
            if (statusCode != 200) {
                throw new RuntimeException("从TikTok获取 access_token 失败, StatusCode: " + statusCode);
            }

            // 解析响应结果
            String result = EntityUtils.toString(response.getEntity(), CharEncoding.UTF_8);
            JsonNode resultJson = new ObjectMapper().readTree(result);
            String code = resultJson.get("code").asText();
            String message = resultJson.get("message").asText();

            if (!"0".equals(code)) {
                throw new RuntimeException(message);
            }

            JsonNode data = resultJson.findValue("data");
            String accessToken = data.get("access_token").asText();
            String refreshToken = data.get("refresh_token").asText();
            String accessTokenExpireIn = data.get("access_token_expire_in").asText();
            String refreshTokenExpireIn = data.get("refresh_token_expire_in").asText();

            LocalDateTime accessTokenExpireTime = CommonUtil.toLocalDateTime(accessTokenExpireIn, false);
            LocalDateTime refreshTokenExpireTime = CommonUtil.toLocalDateTime(refreshTokenExpireIn, false);

            // 写入店铺信息
            TikTokShop shop = new TikTokShop();
            shop.setShopId(tikTokShop.getShopId());
            shop.setAccessToken(accessToken);
            shop.setRefreshToken(refreshToken);
            shop.setAccessTokenExpireTime(accessTokenExpireTime);
            shop.setRefreshTokenExpireTime(refreshTokenExpireTime);

            // 更新 token
            tikTokShop.setAccessToken(accessToken);
            tikTokShopMapper.updateTikTokShopByShopId(shop);
        }
    }
}
