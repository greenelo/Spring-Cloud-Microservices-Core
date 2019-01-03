package com.sssseclipse.web.core.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableTurbineStream
@EnableDiscoveryClient
public class CoreTurbineStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreTurbineStreamApplication.class, args);
	}

}

