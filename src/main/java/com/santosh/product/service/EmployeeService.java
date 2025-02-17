package com.santosh.product.service;

import com.santosh.product.dto.EmployeeDeptDetails;
import com.santosh.product.dto.EmployeeDetail;
import com.santosh.product.dto.EmployeeDetails;
import com.santosh.product.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private RestTemplate restTemplate;


    @Qualifier(value = "restTemplateWithTimeout")
    @Autowired
    private RestTemplate timeOutRestTemplate;

    public EmployeeDetail getEmployee(String empid){
        EmployeeDetail empDtls = new EmployeeDetail();
        CompletableFuture<EmployeeDetails> future1 = CompletableFuture.supplyAsync(() -> getEmployeeNameAndAge(empid));
        CompletableFuture<EmployeeDeptDetails> future2 = CompletableFuture.supplyAsync(() -> getEmployeeDeptAndSal(empid));
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2);

        allFutures.thenAccept(response -> {
            try {
                EmployeeDetails empDetail = future1.get();
                EmployeeDeptDetails empDeptDtls = future2.get();
                empDtls.setId(empDetail.getId());
                empDtls.setName(empDetail.getName());
                empDtls.setAge(empDetail.getAge());
                empDtls.setDept(empDeptDtls.getDept());
                empDtls.setSal(empDeptDtls.getSal());


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        allFutures.join();
        return empDtls;
    }

    private EmployeeDeptDetails getEmployeeDeptAndSal(String empid) {
        EmployeeDeptDetails response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8091/product-api/employee/getemployeedept")
                    .queryParam("empid", empid);
            log.info("Request URL with request params: {}", builder.toUriString());
            log.info("getEmployeeDeptAndSal API called by thread, {}", Thread.currentThread().getName());
            ResponseEntity<EmployeeDeptDetails> responseEntity = timeOutRestTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, EmployeeDeptDetails.class);
            response = responseEntity.getBody();
            log.info("Response With Headers: {}", response);
            log.info("getEmployeeDeptAndSal API responded by thread, {}", Thread.currentThread().getName());
        }catch(Exception ex) {
            log.error("Exception Occured, {}", ex.getMessage());
        }

        return response;
    }

    private EmployeeDetails getEmployeeNameAndAge(String empid) {
        EmployeeDetails response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8091/product-api/employee/getemployee")
                    .queryParam("empid", empid);
            log.info("Request URL with request params: {}", builder.toUriString());
            log.info("getEmployeeNameAndAge API called by thread, {}", Thread.currentThread().getName());
            ResponseEntity<EmployeeDetails> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, EmployeeDetails.class);
            response = responseEntity.getBody();
            log.info("Response With Headers: {}", response);
            log.info("getEmployeeNameAndAge API responded by thread, {}", Thread.currentThread().getName());
        }catch(Exception ex) {
            log.error("Exception Occured, {}", ex.getMessage());
        }

        return response;
    }

    public String testTimeOut(String name) {

        String response = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8091/product-api/employee/testtimeout")
                    .queryParam("name", name);
            ResponseEntity<String> responseEntity = timeOutRestTemplate.exchange(builder.toUriString(),HttpMethod.GET, entity, String.class);
            response = responseEntity.getBody();
            log.info("Response With Headers: {}", response);
        }catch(Exception ex) {
        log.error("Exception occured, {}", ex.getMessage());
        response = "Error";
        }
        return response;


    }
}
