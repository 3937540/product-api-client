package com.santosh.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.santosh.product.dto.ProductDTO;
import com.santosh.product.dto.RequestDTO;
import com.santosh.product.dto.ResponseDTO;
import com.santosh.product.dto.ResponseInfo;

@Service
public interface ProductService {
	
	List<ResponseInfo> getAllProducts();

	ResponseDTO getProductWithHeader();

	ResponseDTO getProductWithParam();

	ResponseInfo addProduct(RequestDTO requestDTO);

	void addProductH2(RequestDTO requestDTO);
}
