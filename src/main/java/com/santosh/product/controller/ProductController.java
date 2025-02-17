package com.santosh.product.controller;

import com.santosh.product.dto.EmployeeDetail;
import com.santosh.product.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.santosh.product.dto.RequestDTO;
import com.santosh.product.dto.ResponseDTO;
import com.santosh.product.dto.ResponseInfo;
import com.santosh.product.service.ProductService;

@Slf4j
@RestController
public class ProductController {
	
	@Autowired
	ProductService service;

	@Autowired
	EmployeeService empService;
	
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
	
	@PostMapping(path = "/addproduct_h2")
	public String addProductH2(@RequestBody RequestDTO requestDTO) {
		
		try {
			service.addProductH2(requestDTO);
		}catch(Exception ex) {
			return "Exception occured";
		}
		return "Product saved successfully";
		
	}

	@GetMapping(path = "/getemployee", produces = {APPLICATION_JSON_VALUE})
	public EmployeeDetail getEmployee(@RequestParam(value = "empid") final String empid){

		return empService.getEmployee(empid);
	}

	@GetMapping(path = "/timeout", produces = {APPLICATION_JSON_VALUE})
	public String testTimeOut(@RequestParam(value = "name") final String name){
		log.info("Timeout endpoint called..");
		return empService.testTimeOut(name);
	}

}
