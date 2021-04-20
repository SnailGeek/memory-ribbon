package com.snail.memory.memoryribbon.demo.command;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.snail.memory.memoryribbon.demo.domain.User;
import org.springframework.web.client.RestTemplate;

/**
 * @program: UserCommand
 * @description:
 * @author: wangf-q
 * @date: 2021-04-19 16:42
 **/
public class UserCommand extends HystrixCommand<User> {

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");
    private RestTemplate restTemplate;
    private Long id;

    public UserCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupName"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandName"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPoolKey")));
    }

    public UserCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    /**
     * 方式一： 实现服务降级
     */
    @Override
    protected User getFallback() {
        // 捕获异常
        Throwable exception = getExecutionException();
        return new User();
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
        HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("CommandKey"), HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));
    }
}
