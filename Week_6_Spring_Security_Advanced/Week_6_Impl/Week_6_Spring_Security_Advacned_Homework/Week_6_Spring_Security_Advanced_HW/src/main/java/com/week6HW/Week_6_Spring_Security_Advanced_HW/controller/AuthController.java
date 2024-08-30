package com.week6HW.Week_6_Spring_Security_Advanced_HW.controller;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.advice.ApiResponse;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.*;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.service.AuthService;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.service.SessionService;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthService authService;
	private final SessionService sessionService;

	@Value("${deploy.env}")
	private String deployeEnv;

	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
		UserDto userDto = userService.signUp(signUpDto);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto , HttpServletRequest request, HttpServletResponse response){
		LoginResponseDto loginResponseDto = authService.login(loginDto);

		Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
		cookie.setHttpOnly(true);
		cookie.setSecure("production".equals(deployeEnv));
		response.addCookie(cookie);

		return ResponseEntity.ok(loginResponseDto);
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
		String refreshToken = Arrays.stream(request.getCookies())
				.filter(cookie -> "refreshToken".equals(cookie.getName()))
				.findFirst()
				.map(Cookie::getValue)
				.orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
		LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);
		return ResponseEntity.ok(loginResponseDto);
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Object>> logout(HttpServletRequest request){
		String refreshToken = Arrays.stream(request.getCookies())
				.filter(cookie -> "refreshToken".equals(cookie.getName()))
				.findFirst()
				.map(Cookie::getValue)
				.orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));
		SessionDto sessionDto = sessionService.getSessionFromRefreshToken(refreshToken);
		userService.updateMaxLogin(sessionDto.getUser());
		boolean flag = sessionService.deleteByToken(refreshToken);
		if(flag){
			Cookie cookie = new Cookie("refreshToken", null);
			cookie.setHttpOnly(true);
			cookie.setSecure("production".equals(deployeEnv));
			cookie.setMaxAge(0);
			return ResponseEntity.ok(ApiResponse.builder().message("Successfully Logout").status(HttpStatus.OK).build());
		}

		return ResponseEntity.ok(ApiResponse.builder().message("Already Logout").status(HttpStatus.NOT_FOUND).build());
	}


}
