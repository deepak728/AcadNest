package com.project.acadNest.people.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AcadNestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcadNestApplication.class, args);
	}

}
