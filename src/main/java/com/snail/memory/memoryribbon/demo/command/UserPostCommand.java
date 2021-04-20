package com.snail.memory.memoryribbon.demo.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.snail.memory.memoryribbon.demo.domain.User;
import org.springframework.web.client.RestTemplate;

/**
 * @program: UserPostCommand
 * @description:
 * @author: wangf-q
 * @date: 2021-04-20 12:40
 **/
public class UserPostCommand extends HystrixCommand<User> {
    private RestTemplate restTemplate;
    private User user;

    public UserPostCommand(RestTemplate restTemplate, User user) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetSetGet")));
        this.restTemplate = restTemplate;
        this.user = user;
    }

    @Override
    protected User run() throws Exception {
        // 写操作
        User user = restTemplate.postForObject("http://USER_SEVICE/users", this.user, User.class);
        // 刷新缓存，清理缓存中失效的User
        UserGetCommand.flushCache(user.getId());
        return user;
    }
}
