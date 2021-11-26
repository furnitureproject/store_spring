package com.team.store_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "com.team.entity" })
@ComponentScan(basePackages = { "com.team.controller", "com.team.service", "com.team.controller.user",
		"com.team.controller.product", "com.team.controller.category", "com.team.controller.seller",
		"com.team.security", "com.team.jwt", "com.team.mybatis" })
@EnableJpaRepositories(basePackages = { "com.team.repository" })
public class StoreSpringApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(StoreSpringApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(StoreSpringApplication.class, args);
		System.out.println("SERVER RUN");
	}

}
