package com.app.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages={"com.app.*"})
public class ArcuserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArcuserApplication.class, args);
	}
}
