package com.snail.memory.memoryribbon.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.snail.memory.memoryribbon.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @program: UserCacheService
 * @description:
 * @author: wangf-q
 * @date: 2021-04-20 19:23
 **/
@Service
public class UserCacheService {
    @Autowired
    private RestTemplate restTemplate;

    @CacheResult
    @HystrixCommand
    public User getUserById(Long id) {
        return restTemplate.getForObject("http://USER_SERVICE/users/{1}", User.class, id);
    }

    @CacheResult(cacheKeyMethod = "getUserByIdCacheKey")
    @HystrixCommand
    public User getUserByIdAssignKey(Long id) {
        return restTemplate.getForObject("http://USER_SERVICE/users/{1}", User.class, id);
    }

    private Long getUserByIdCacheKey(Long id) {
        return id;
    }

    @CacheResult
    @HystrixCommand
    public User getUserCacheKey(@CacheKey("id") Long id) {
        return restTemplate.getForObject("http://USER_SERVICE/users/{1}", User.class, id);
    }

    @CacheRemove(commandKey = "getUserById")
    @HystrixCommand
    public User update(@CacheKey("id") User user) {
        return restTemplate.postForObject("http://USER_SERVICE/users", user, User.class);
    }

}
