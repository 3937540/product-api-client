package com.santosh.product.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"id", "productName", "productQuantity", "productPrice"})
public class ResponseInfo {
	
	private long id;
	private int productQuantity;
	private BigDecimal productPrice;
	private String productName;
	

}
