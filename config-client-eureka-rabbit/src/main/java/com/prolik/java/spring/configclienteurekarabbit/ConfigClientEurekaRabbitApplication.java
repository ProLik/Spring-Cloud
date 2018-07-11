package com.prolik.java.spring.configclienteurekarabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientEurekaRabbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientEurekaRabbitApplication.class, args);
	}
}
