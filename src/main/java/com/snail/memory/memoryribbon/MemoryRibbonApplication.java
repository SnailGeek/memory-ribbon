package com.snail.memory.memoryribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MemoryRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemoryRibbonApplication.class, args);
    }

}
