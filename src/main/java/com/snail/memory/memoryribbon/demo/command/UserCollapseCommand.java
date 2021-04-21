package com.snail.memory.memoryribbon.demo.command;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.snail.memory.memoryribbon.demo.domain.User;
import com.snail.memory.memoryribbon.demo.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: UserBatchCommand
 * @description:
 * @author: wangf-q
 * @date: 2021-04-20 19:50
 **/
public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Long> {

    private UserService userService;
    Long userId;

    public UserCollapseCommand(UserService userService, Long userId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapseCommand"))
                .andCollapserPropertiesDefaults(
                        HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userId = userId;
        this.userService = userService;
    }

    @Override
    public Long getRequestArgument() {
        return userId;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        List<Long> userIds = new ArrayList<>(collapsedRequests.size());
        userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserBatchCommand(userService, userIds);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<User, Long> collapsedRequest : collapsedRequests) {
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }
}
