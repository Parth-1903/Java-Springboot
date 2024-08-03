package com.week4.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
public class RestClientConfig {

	@Value("${departmentService.base.url}")
	private String BASE_URL;

	@Bean
	@Qualifier("departmentRestClient")
	RestClient getDepartmentServiceRestClient(){
		return RestClient.builder()
				.baseUrl(BASE_URL)
				.defaultHeader(CONTENT_TYPE,APPLICATION_JSON_VALUE)
				.defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
						throw new RuntimeException("Server error occurred");
				})
				.build();
	}
}
