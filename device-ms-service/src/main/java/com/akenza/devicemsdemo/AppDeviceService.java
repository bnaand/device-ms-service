package com.akenza.devicemsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class AppDeviceService {

	public static void main(String[] args) {
		SpringApplication.run(AppDeviceService.class, args);
	}

}
