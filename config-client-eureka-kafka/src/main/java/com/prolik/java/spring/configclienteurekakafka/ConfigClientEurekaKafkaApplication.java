package com.prolik.java.spring.configclienteurekakafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientEurekaKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigClientEurekaKafkaApplication.class, args);
	}
}
