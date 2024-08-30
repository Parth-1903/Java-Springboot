package com.week4.Clients.impl;

import com.week4.Clients.DepartmentClient;
import com.week4.advice.ApiResponse;
import com.week4.dto.DepartmentDto;
import com.week4.exception.ResourceNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentClientImpl implements DepartmentClient {

	private final RestClient restClient;

	Logger log = LoggerFactory.getLogger(DepartmentClientImpl.class);
	@Override
	public List<DepartmentDto> getAllDepartments() {
//		log.error("error long");
//		log.warn("warn long");
//		log.info("info long");
////		========== Upto this level it would work ================
//		//If you want to specified debug and trace as well then you need to specify that
//		//in application.properties
//		log.debug("debug long");
//		log.trace("trace long");
	    try{
		    ApiResponse<List<DepartmentDto>> departmentDtoList = restClient.get()
				    .uri("v1/department")
				    .retrieve()
				    .body(new ParameterizedTypeReference <>() {
				    });
		    log.info("Successfully retrieved the department in getAlLDepartments");
			log.trace("Retrieved department list in getAllEmployees", departmentDtoList.getData());
		    log.trace("Retrieved department list in getAllEmployees: {}, {}, {}", departmentDtoList.getData(),"Hello","2");
		    return departmentDtoList.getData();
	    }catch (Exception e){
			log.error("Exception occured in getAllDepartments()",e);
			throw new RuntimeException(e);
	    }
	}

	@Override
	public DepartmentDto getDepartmentById(Long deptId) {
		try{
			ResponseEntity<ApiResponse<DepartmentDto>> departmentDto = restClient.get()
					.uri("/v1/department/{id}", deptId)
					.retrieve()
					.onStatus(HttpStatusCode::is4xxClientError, (req,res) -> {
						log.error(new String(res.getBody().readAllBytes()));
						throw new ResourceNotFoundException("could not create the employees");
					})
					// if you don't want to write this server error again and again use it in RestClientConfig
//					.onStatus(HttpStatusCode::is5xxServerError, (req,res) -> {
//						throw new RuntimeException("Server error occured");
//					})
					//If you want whole response entity then use toEntity().
					//If you want only body then use body().
					.toEntity(new ParameterizedTypeReference<>() {
					});
			return departmentDto.getBody().getData();
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
}
