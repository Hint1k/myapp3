package com.demo.fds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:kafka.properties")
@EnableCaching
public class TransactionMain {

    public static void main(String[] args) {
        SpringApplication.run(TransactionMain.class, args);
    }
}