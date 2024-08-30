package com.week6HW.Week_6_Spring_Security_Advanced_HW.service;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.LoginDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.LoginResponseDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;
	private final SessionService sessionService;

	@Transactional
	public LoginResponseDto login(LoginDto loginDto){
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
		);
		User user = (User) authentication.getPrincipal();

		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);

		sessionService.generateNewSession(user, refreshToken);

		return new LoginResponseDto(user.getId(), accessToken, refreshToken);
	}

	public LoginResponseDto refreshToken(String refreshToken){
		Long userId = jwtService.getUserIdFromToken(refreshToken);
//		sessionService.validateSession(refreshToken);

		User user = userService.getUserById(userId);

		String accessToken = jwtService.generateAccessToken(user);

		sessionService.generateNewSession(user, refreshToken);

		return new LoginResponseDto(user.getId(), accessToken, refreshToken);

	}

}
