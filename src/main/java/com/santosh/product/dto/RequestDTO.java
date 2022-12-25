package com.santosh.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class RequestDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String productName;
	private int productQuantity;
	private BigDecimal productPrice;
}
