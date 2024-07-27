package com.uber.services.impl;

import com.uber.dto.DriverDto;
import com.uber.dto.SignupDto;
import com.uber.dto.UserDto;
import com.uber.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
	@Override
	public String login(String email, String password) {
		return null;
	}

	@Override
	public UserDto signup(SignupDto signupDto) {
		return null;
	}

	@Override
	public DriverDto onboardNewDriver(Long userId) {
		return null;
	}
}
