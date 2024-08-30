package com.week5_homework.services;

import com.week5_homework.dto.LoginDto;
import com.week5_homework.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final SessionService sessionService;

	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword())
		);
		User user = (User) authentication.getPrincipal();
		String token = jwtService.generateToken(user);
		if(!sessionService.getValidSession(user,token)){
			throw new CredentialsExpiredException("Session already expired!");
		}
		return token;
	}
}
