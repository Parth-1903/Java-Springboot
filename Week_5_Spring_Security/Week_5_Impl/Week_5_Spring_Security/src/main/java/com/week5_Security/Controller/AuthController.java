package com.week5_Security.Controller;

import com.week5_Security.Dto.LoginDto;
import com.week5_Security.Dto.SignUpDto;
import com.week5_Security.Dto.UserDto;
import com.week5_Security.Service.AuthService;
import com.week5_Security.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
		UserDto userDto = userService.signUp(signUpDto);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response){
		String token = authService.login(loginDto);

		Cookie cookie = new Cookie("token",token);
		cookie.setHttpOnly(true); //It is accessed by HTTP not JS.
//		cookie.setSecure(true); //It is access by only Secure HTTPS only.
		response.addCookie(cookie);

		return ResponseEntity.ok(token);
	}

}
