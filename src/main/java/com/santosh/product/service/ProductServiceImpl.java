package com.santosh.product.service;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.santosh.product.config.ProductConfig;
import com.santosh.product.dto.ProductsResponse;
import com.santosh.product.dto.RequestDTO;
import com.santosh.product.dto.ResponseDTO;
import com.santosh.product.dto.ResponseInfo;
import com.santosh.product.entity.ProductDetail;
import com.santosh.product.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ProductConfig config;

	@Override
	public List<ResponseInfo> getAllProducts() {
		
		ProductsResponse responseDTO = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<ProductsResponse> response = restTemplate.exchange(config.getUrlAllProduct(), HttpMethod.GET, entity, ProductsResponse.class);
			
			responseDTO = response.getBody();
			log.info("responseDTO = " + responseDTO);
			
		}catch(HttpClientErrorException httpEx) {
			
			log.error("Http Client Error status code: {}, Error Message: {}", httpEx.getStatusCode(), httpEx.getResponseBodyAsString());
			//Throw Exception if you want.
			
		}catch(HttpServerErrorException serverEx) {
			
			log.error("Http Server Error status code: {}, Error Message: {}", serverEx.getStatusCode(), serverEx.getResponseBodyAsString());
			//Throw Exception if you want.
		}catch(Exception ex) {
			
			log.error("Error Occured: {}", ex.getMessage());
			//Throw Exception if you want.
		}
		
		
		return responseDTO.getResultDTO();
		
	}

	@Override
	public ResponseDTO getProductWithHeader() {
		
		ResponseDTO response = null;
		try {
			
			HttpEntity<String> entity = new HttpEntity<>(getHeaders());
			ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(config.getUrlWithHeaders(),HttpMethod.GET, entity, ResponseDTO.class);
			response = responseEntity.getBody();
			log.info("Response With Headers: {}", response);
		}catch(Exception ex) {
			
		}
		return response;
	}

	private HttpHeaders getHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("p-id", "8");
		headers.set("p-name", "Deo");
		log.info("Request Headers: {}", headers);
		return headers;
	}

	@Override
	@Retryable(value = {IllegalStateException.class, ConnectException.class}, maxAttemptsExpression = "#{${retry.max.attempts}}", backoff = @Backoff(delayExpression = "#{${retry.backoff.ms}}"))
	public ResponseDTO getProductWithParam() {
		ResponseDTO response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(config.getUrlWithParams())
					.queryParam("productId", 7)
					.queryParam("productNm", "Brush");
			log.info("Request URL with request params: {}", builder.toUriString());
			ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(builder.toUriString(),HttpMethod.GET, entity, ResponseDTO.class);
			response = responseEntity.getBody();
			log.info("Response With Headers: {}", response);
		}catch(Exception ex) {
			
		}
		return response;
	}
	
	//Return type of recover method and actual method should be same.
	@Recover
	public ResponseDTO recoverMethod() {
		ResponseDTO response = new ResponseDTO();
		//Set response values and return.
		return response;
	}

	@Override
	public ResponseInfo addProduct(RequestDTO requestDTO) {
		
		log.info("Calling Add Product service provider with request: {}", requestDTO);
		ResponseInfo response = null;
		try {
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<RequestDTO> entity = new HttpEntity<>(requestDTO, headers);
			ResponseEntity<ResponseDTO> responseEntity = restTemplate.exchange(config.getUrlAddProduct(), HttpMethod.POST, entity, ResponseDTO.class);
			if(null != responseEntity) {
				response = responseEntity.getBody().getResultDTO();
			}
		}catch(HttpClientErrorException httpEx) {
			log.error("Client Error occured with status code: {} and error message: {}", httpEx.getStatusCode(), httpEx.getResponseBodyAsString());
			//throw custom exception.
		}catch(HttpServerErrorException serverEx) {
			log.error("Server Error occured with status code: {} and error message: {}", serverEx.getStatusCode(), serverEx.getResponseBodyAsString());
			//throw custom exception.
		}
		return response;
	}

	@Override
	public void addProductH2(RequestDTO requestDTO) {
		
		ProductDetail product = new ProductDetail();
		product.setProductNm(requestDTO.getProductName());
		product.setProductPrc(requestDTO.getProductPrice());
		product.setProductQty(requestDTO.getProductQuantity());
		
		repository.save(product);
		log.info("Product details saved into H2 DB.");
	}
}
