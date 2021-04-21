package com.snail.memory.memoryribbon.demo.command;

import com.netflix.hystrix.HystrixCommand;
import com.snail.memory.memoryribbon.demo.domain.User;
import com.snail.memory.memoryribbon.demo.service.UserService;

import java.util.List;

import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

/**
 * @program: UserBatchCommand
 * @description:
 * @author: wangf-q
 * @date: 2021-04-20 19:50
 **/
public class UserBatchCommand extends HystrixCommand<List<User>> {

    private UserService userService;
    List<Long> userIds;

    public UserBatchCommand(UserService userService, List<Long> userIds) {
        super(Setter.withGroupKey(asKey("userServiceCommand")));
        this.userIds = userIds;
        this.userService = userService;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.findAll(userIds);
    }
}
