package com.cow8k.stock.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author LiuSheng
 * {@code @date} 2025/9/18 21:14
 */


public class CommonUtil {

    /**
     * 时间戳字符串转 LocalDateTime
     * @param timestampStr 时间戳字符串
     * @param isMillis 是否毫秒时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String timestampStr, boolean isMillis) {
        long ts = Long.parseLong(timestampStr);
        if (isMillis) {
            return Instant.ofEpochMilli(ts).atZone(ZoneId.systemDefault()).toLocalDateTime();
        } else {
            return LocalDateTime.ofEpochSecond(ts, 0, ZoneOffset.ofHours(8));
        }
    }

}
