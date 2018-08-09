package com.just;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserRunApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(UserRunApplication2.class, args);
	}
}
