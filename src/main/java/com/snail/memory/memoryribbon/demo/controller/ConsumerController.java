package com.snail.memory.memoryribbon.demo.controller;

import com.snail.memory.memoryribbon.utils.RequestHeaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @program: ConsumreController
 * @description:
 * @author: wangf-q
 * @date: 2021-04-16 10:05
 **/
@RestController
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon-consumer")
    public String helloConsumer() {
        String url = "http://MEMORY/hello";
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, RequestHeaderUtils.buildRequestHeader());
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {
        });
        return exchange.getBody();
    }


}
