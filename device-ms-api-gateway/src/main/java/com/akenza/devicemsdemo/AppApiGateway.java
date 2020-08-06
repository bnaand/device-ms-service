package com.akenza.devicemsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class AppApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(AppApiGateway.class, args);
    }

}