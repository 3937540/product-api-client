package com.santosh.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.santosh.product.dto.RequestDTO;
import com.santosh.product.dto.ResponseDTO;
import com.santosh.product.dto.ResponseInfo;
import com.santosh.product.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@GetMapping(path = "/getallproducts")
	public List<ResponseInfo> getAllProducts() {
		
		return service.getAllProducts();
		
	}
	
	@GetMapping(path = "/getproductwithheader")
	public ResponseDTO getProductWithHeader() {
		
		return service.getProductWithHeader();

	}
	
	@GetMapping(path = "/getproductwithparam")
	public ResponseDTO getProductWithParam() {
		
		return service.getProductWithParam();

	}
	
	@PostMapping(path = "/addproduct")
	public ResponseInfo addProduct(@RequestBody RequestDTO requestDTO) {
		
		return service.addProduct(requestDTO);
		
	}
}
