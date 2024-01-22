package com.example.records.reportapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "com.example.records.reportapi")
@EntityScan("com.example.records.reportapi")
@ComponentScan("com.example.records.reportapi.service")
// @ComponentScan("com.example.records.reportapi.controller")
@EnableJpaRepositories(basePackages = "com.example.records.reportapi.repository")
public class ReportApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportApiApplication.class, args);
	}

}
