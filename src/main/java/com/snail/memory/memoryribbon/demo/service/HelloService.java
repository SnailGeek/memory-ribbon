package com.snail.memory.memoryribbon.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.snail.memory.memoryribbon.utils.RequestHeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: HelloService
 * @description:
 * @author: wangf-q
 * @date: 2021-04-16 12:45
 **/
@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback", commandKey = "helloKey")
    public String helloConsumer() {
        String url = "http://MEMORY/hello";
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(null, RequestHeaderUtils.buildRequestHeader());
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {
        });
        return exchange.getBody();
    }

    public String helloFallback() {
        return "error";
    }
}
