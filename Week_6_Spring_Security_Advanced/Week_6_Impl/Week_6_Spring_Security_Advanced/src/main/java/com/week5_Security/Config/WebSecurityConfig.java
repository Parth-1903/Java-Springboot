package com.week5_Security.Config;

import com.week5_Security.entities.enums.Permission;
import com.week5_Security.filter.JwtAuthFilter;
import com.week5_Security.handlers.OAuth2SuccessHandler;
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

import static com.week5_Security.entities.enums.Permission.*;
import static com.week5_Security.entities.enums.Role.ADMIN;
import static com.week5_Security.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;

	private static final String[] publicRoutes = {
			"/error","/auth/**", "/home.html"
	};

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
						.requestMatchers(publicRoutes).permitAll()
						.requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(), CREATOR.name())
						.requestMatchers(HttpMethod.POST,"/posts/**").hasAnyAuthority(POST_CREATE.name())
//						.requestMatchers(HttpMethod.GET,"/posts/**").hasAuthority(POST_VIEW.name())
						.requestMatchers(HttpMethod.PUT,"/posts/**").hasAuthority(POST_UPDATE.name())
						.requestMatchers(HttpMethod.DELETE,"/posts/**").hasAuthority(POST_DELETE.name())
//						.requestMatchers("/posts/**").authenticated()
						.anyRequest().authenticated())

				.csrf(csrf -> csrf.disable()) //disabled CSRF
				.sessionManagement(sessionConfig -> //We are going for STATELESS
						sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.oauth2Login(oauth2Config ->
						oauth2Config
								.failureUrl("/login?error=true")
								.successHandler(oAuth2SuccessHandler)
				);

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
