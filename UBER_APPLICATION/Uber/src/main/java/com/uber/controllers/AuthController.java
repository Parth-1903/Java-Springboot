package com.uber.controllers;

import com.uber.advices.ApiResponse;
import com.uber.dto.SignupDto;
import com.uber.dto.UserDto;
import com.uber.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	ApiResponse<UserDto> signUp(@RequestBody SignupDto signupDto){
		return new ApiResponse<>(HttpStatus.OK.value(),"Sign up Successfully", authService.signup(signupDto));
	}
}
