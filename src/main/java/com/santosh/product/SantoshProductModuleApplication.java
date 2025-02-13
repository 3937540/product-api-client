package com.santosh.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class SantoshProductModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SantoshProductModuleApplication.class, args);
	}

}
