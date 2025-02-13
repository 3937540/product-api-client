package com.santosh.product;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.santosh.product.dto.ProductsResponse;
import com.santosh.product.dto.ResponseInfo;
import com.santosh.product.service.ProductService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SantoshProductModuleApplicationTests {

	@Autowired
	ProductService service;

	@MockBean
	RestTemplate restTemplate;

	@Test
	public void testRestTemplateWithMocking() {
		
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.eq(HttpMethod.GET), Mockito.any(HttpEntity.class), ProductsResponse.class))
			.thenReturn(getResponse());
		
		/*String response = restTemplate.getForObject(builder.toUriString(), String.class, entity);
		Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(String.class), Mockito.any(HttpEntity.class)))
		.thenReturn("");*/
		
		List<ResponseInfo> responseInfo = service.getAllProducts();
		assertNotNull(responseInfo);
		System.out.println(responseInfo);
	}

	private ResponseEntity<ProductsResponse> getResponse() {
		
		ProductsResponse response = new ProductsResponse();
		ResponseInfo responseInfo = new ResponseInfo();
		ArrayList<ResponseInfo> listOfProducts = new ArrayList<>();
		responseInfo.setId(25);
		responseInfo.setProductName("Test");
		listOfProducts.add(responseInfo);
		response.setReturnCode(200);
		response.setStatus("Success");
		response.setReturnMessage("Successfully got the Products");
		response.setResultDTO(listOfProducts);
		return new ResponseEntity<ProductsResponse>(response, HttpStatus.OK);
	}

}
