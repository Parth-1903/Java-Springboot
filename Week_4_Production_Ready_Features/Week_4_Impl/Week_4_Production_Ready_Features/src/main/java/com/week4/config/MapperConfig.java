package com.week4.config;

import com.week4.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")
public class MapperConfig {

	@Bean
	ModelMapper getModelMapper(){
		return new ModelMapper();
	}

	@Bean
	AuditorAware<String> getAuditorAwareImpl(){
		return new AuditorAwareImpl();
	}
}
