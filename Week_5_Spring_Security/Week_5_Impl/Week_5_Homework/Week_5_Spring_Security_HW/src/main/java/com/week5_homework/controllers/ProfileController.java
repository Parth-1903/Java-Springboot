package com.week5_homework.controllers;

import com.week5_homework.dto.UserDto;
import com.week5_homework.entities.User;
import com.week5_homework.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final UserService userService;

	@GetMapping("/getProfile")
	public ResponseEntity<UserDto> getProfile(){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return ResponseEntity.ok(userService.getUserDtoFromUser(user));
	}

}
