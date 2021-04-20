package com.snail.memory.memoryribbon.demo.domain;

import lombok.Data;

/**
 * @program: User
 * @description:
 * @author: wangf-q
 * @date: 2021-04-19 21:22
 **/
@Data
public class User {
    private String name;
    private Long id;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }
}
