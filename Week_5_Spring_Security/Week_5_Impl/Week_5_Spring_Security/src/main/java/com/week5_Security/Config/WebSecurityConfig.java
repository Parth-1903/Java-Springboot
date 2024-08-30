package com.week5_Security.Config;

import com.week5_Security.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		//This will only authenticate this page only
//		httpSecurity
//				.formLogin(formLoginConfig -> formLoginConfig
//						.loginPage("/newLogin.html"))
//				.


//		httpSecurity.formLogin(Customizer.withDefaults());

		//Authenticate All API's
//		httpSecurity
//				.authorizeHttpRequests(auth -> auth
//						.anyRequest().authenticated())
//				.formLogin(Customizer.withDefaults());

		//Customize your API's authenticate
		httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/posts","/error","/auth/**").permitAll()
//						.requestMatchers("/posts/**").hasAnyRole("ADMIN")
						.requestMatchers("/posts/**").authenticated()
						.anyRequest().authenticated())

				.csrf(csrf -> csrf.disable()) //disabled CSRF
				.sessionManagement(sessionConfig -> //We are going for STATELESS
						sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//				.formLogin(Customizer.withDefaults());

		return httpSecurity.build();
	}
// For activation of InMemoryUser then you can use it and remove @Service on UserService
//	@Bean
//	UserDetailsService myInMemoryUserDetailsService(){
//		UserDetails userDetails = User
//				.withUsername("Parth")
//				.password(passwordEncoder().encode("root"))
//				.roles("USER")
//				.build();
//
//		UserDetails adminDetails = User
//				.withUsername("Krisha")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(userDetails, adminDetails);
//	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
