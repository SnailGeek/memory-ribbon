package com.snail.memory.memoryribbon.demo.controller;

import com.snail.memory.memoryribbon.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: ConsumreController
 * @description:
 * @author: wangf-q
 * @date: 2021-04-16 10:05
 **/
@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/ribbon-consumer")
    public String helloConsumer() {
        return helloService.helloConsumer();
    }
}
