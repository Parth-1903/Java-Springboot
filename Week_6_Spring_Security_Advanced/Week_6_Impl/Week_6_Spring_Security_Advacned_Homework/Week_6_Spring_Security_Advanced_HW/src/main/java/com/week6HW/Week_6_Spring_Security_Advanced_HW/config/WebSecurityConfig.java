package com.week6HW.Week_6_Spring_Security_Advanced_HW.config;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.enums.Role.ADMIN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;


	private static final String[] publicRoutes = {
			"/error","/auth/**"
	};

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(publicRoutes).permitAll()
						.requestMatchers(HttpMethod.POST,"/shows/**").hasAnyRole(ADMIN.name())
						.requestMatchers(HttpMethod.DELETE,"/shows/**").hasAnyRole(ADMIN.name())
						.requestMatchers(HttpMethod.PUT,"/shows/**").hasAnyRole(ADMIN.name())
						.anyRequest().authenticated())
				.csrf(csrf -> csrf.disable())
				.sessionManagement(sessionConfig ->
						sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
