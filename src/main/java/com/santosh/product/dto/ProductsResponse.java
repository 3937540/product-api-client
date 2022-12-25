package com.santosh.product.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"status", "returnCode", "returnMessage", "resultDTO"})
public class ProductsResponse {

	private int returnCode;
	private String returnMessage;
	private String status;
	private List<ResponseInfo> resultDTO;
}
