package com.app.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages={"com.app.*"})
public class ArcexamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArcexamApplication.class, args);
	}
}
