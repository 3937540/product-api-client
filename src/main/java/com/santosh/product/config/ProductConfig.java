package com.santosh.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class ProductConfig {
	
	@Value("${fetch.all.products}")
	private String urlAllProduct; 
	
	@Value("${fetch.product.with.headers}")
	private String urlWithHeaders;

	@Value("${fetch.product.with.params}")
	private String urlWithParams;
	
	@Value("${service.add.product.url}")
	private String urlAddProduct;
}
