package com.prolik.java.spring.hystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


/**
 * Hystrix Dashboard 支持三种不同的监控方式
 * 1.默认的集群监控
 * 		http://turbine-hostname:port/turbine.stream
 * 2.指定的集群监控
 * 		http://turbine-hostname:port/turbine.stream?cluster=[clusterName]
 * 3.单体应用的监控
 * 		http://hystrix-app:port/hystrix.stream
 *
 */
@SpringBootApplication
//启动Hystrix Dashboard
@EnableHystrixDashboard
public class HystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardApplication.class, args);
	}
}
