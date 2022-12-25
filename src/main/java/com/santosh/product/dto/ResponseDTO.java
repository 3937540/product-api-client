package com.santosh.product.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"status", "returnCode", "returnMessage", "resultDTO"})
public class ResponseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int returnCode;
	private String returnMessage;
	private String status;
	private ResponseInfo resultDTO;

}
