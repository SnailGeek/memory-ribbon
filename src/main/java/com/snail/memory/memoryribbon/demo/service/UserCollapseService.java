package com.snail.memory.memoryribbon.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.snail.memory.memoryribbon.demo.domain.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * @program: UserCollapseService
 * @description:
 * @author: wangf-q
 * @date: 2021-04-20 20:34
 **/
@Service
public class UserCollapseService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCollapser(batchMethod = "findAll", collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "100")})
    public User find(Long id) {
        return null;
    }

    @HystrixCommand
    public List<User> findAll(List<Long> ids) {
        return restTemplate.getForObject("http://USER-SERVICSE/users/ids={1}", List.class, StringUtils.join(ids, ","));
    }

}
