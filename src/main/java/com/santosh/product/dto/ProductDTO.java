package com.santosh.product.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Product id shouldn't be null")
	private Long productId;
	@NotEmpty(message = "Product name shouldn't be null or empty")
	private String productName;
	private int productQuantity;
	private BigDecimal productPrice;
}
