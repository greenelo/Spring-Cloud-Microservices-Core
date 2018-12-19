package com.sssseclipse.web.core.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CoreEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreEurekaApplication.class, args);
	}

}

