package com.snail.memory.memoryribbon.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.snail.memory.memoryribbon.demo.domain.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @program: UserServie
 * @description:
 * @author: wangf-q
 * @date: 2021-04-19 21:02
 **/
@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;


    public User find(Long id) {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    public List<User> findAll(List<Long> ids) {
        return restTemplate.getForObject("http://USER-SERVICE/users?ids={1}", List.class, StringUtils.join(ids, ","));
    }

    @HystrixCommand(fallbackMethod = "defaultUser")
    public User getUserById(Long id) {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    @HystrixCommand(fallbackMethod = "defaultUserSec", commandKey = "getUserById", groupKey = "UserGroup", threadPoolKey = "getUserByIdThread")
    public User defaultUser(Long id, Throwable e) {
        String errorMessage = e.getMessage();
        return new User("First Fallback");
    }

    public User defaultUserSec() {
        return new User("Second Fallback");
    }


    @HystrixCommand
    public Future<User> getUserByIdAsync(final String id) {
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
            }
        };
    }

    @HystrixCommand
    public Observable<User> getUserById(final String id) {
        return Observable.unsafeCreate(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        User user = restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                } catch (RestClientException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
