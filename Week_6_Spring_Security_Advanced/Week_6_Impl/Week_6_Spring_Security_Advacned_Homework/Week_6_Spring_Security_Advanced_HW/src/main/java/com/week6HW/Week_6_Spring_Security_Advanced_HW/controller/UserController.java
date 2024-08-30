package com.week6HW.Week_6_Spring_Security_Advanced_HW.controller;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.ProfileDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.User;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final ModelMapper modelMapper;

	@GetMapping("/profile")
	public ResponseEntity<ProfileDto> getProfile(){
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(modelMapper.map(user, ProfileDto.class));
	}

}
