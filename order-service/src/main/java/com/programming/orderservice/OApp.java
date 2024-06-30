package com.programming.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableDiscoveryClient
public class OApp {
    public static void main(String[] args) {
        SpringApplication.run(OApp.class,args);
    }
}