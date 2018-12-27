package com.sssseclipse.web.core.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.mongodb.EnableMongoConfigServer;

@EnableDiscoveryClient
@SpringBootApplication
@EnableMongoConfigServer
public class CoreConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreConfigApplication.class, args);
	}

}

