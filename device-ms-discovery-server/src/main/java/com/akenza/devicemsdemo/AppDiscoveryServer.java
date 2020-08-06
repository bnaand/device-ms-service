package com.akenza.devicemsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AppDiscoveryServer {

    public static void main(String[] args) {
        SpringApplication.run(AppDiscoveryServer.class, args);
    }
}