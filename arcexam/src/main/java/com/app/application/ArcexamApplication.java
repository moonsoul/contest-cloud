package com.app.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.app.*"})
public class ArcexamApplication {
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return (RestTemplate)SpringUtil.getBean("restTemplate1");
	}

	public static void main(String[] args) {
		SpringApplication.run(ArcexamApplication.class, args);
	}
}
