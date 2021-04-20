package com.snail.memory.memoryribbon.demo.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.snail.memory.memoryribbon.demo.domain.User;
import org.springframework.web.client.RestTemplate;

/**
 * @program: UserGetCommand
 * @description:
 * @author: wangf-q
 * @date: 2021-04-20 12:37
 **/
public class UserGetCommand extends HystrixCommand<User> {
    private RestTemplate restTemplate;
    private Long id;
    private static final HystrixCommandKey GTTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");

    public UserGetCommand(RestTemplate restTemplate, Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    /**
     * 开启缓存功能
     *
     * @return
     */
    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    public static void flushCache(Long id) {
        // 刷新缓存，清理id
        HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("CommandKey"),
                HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));
    }
}
