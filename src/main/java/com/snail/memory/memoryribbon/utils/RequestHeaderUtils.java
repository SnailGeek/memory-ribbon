package com.snail.memory.memoryribbon.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * @program: RequestHeaderUtils
 * @description:
 * @author: wangf-q
 * @date: 2021-04-16 12:28
 **/
public class RequestHeaderUtils {
    public static HttpHeaders buildRequestHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        // 缺少这个头，在通过负载均衡调用的时候会报错
        requestHeaders.add("Content-Type", "application/json;charset=utf-8");
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        return requestHeaders;
    }
}
