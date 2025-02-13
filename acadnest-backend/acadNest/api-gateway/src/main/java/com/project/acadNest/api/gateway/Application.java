package com.project.acadNest.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.project.acadNest")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
