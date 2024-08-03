package com.week4HW.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

	@Value("${currencyConverter.base.url}")
	private String BASE_URL;

	private final ObjectMapper objectMapper;

	@Bean
	@Qualifier("currencyConvertRestClient")
	RestClient getCurrencyServiceRestClient() {
		return RestClient.builder()
				.baseUrl(BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
					throw new RuntimeException("Server error occurred");
				})
				.build();
	}

	//This will only work with the commented line of the CurrencyClientImpl

//	@Bean
//	@Qualifier("currencyConvertRestClient")
//	RestClient getCurrencyServiceRestClient() {
//		return RestClient.builder()
//				.baseUrl(BASE_URL+"?apikey="+API_KEY)
//				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				.defaultStatusHandler(HttpStatusCode::is5xxServerError, (req, res) -> {
//					throw new RuntimeException("Server error occurred");
//				})
//				.build();
//	}

}
