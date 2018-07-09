package com.prolik.java.spring.feigncunsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignCunsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignCunsumerApplication.class, args);
	}
}
