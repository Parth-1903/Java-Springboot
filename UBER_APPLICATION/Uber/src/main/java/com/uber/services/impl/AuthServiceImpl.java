package com.uber.services.impl;

import com.uber.dto.DriverDto;
import com.uber.dto.SignupDto;
import com.uber.dto.UserDto;
import com.uber.entities.Rider;
import com.uber.entities.User;
import com.uber.entities.enums.Role;
import com.uber.exceptions.RuntimeConflictException;
import com.uber.repositories.UserRepository;
import com.uber.services.AuthService;
import com.uber.services.RiderService;
import com.uber.services.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final ModelMapper modelMapper;
	private final UserRepository userRepository;
	private final RiderService riderService;
	private final WalletService walletService;

	@Override
	public String login(String email, String password) {
		return null;
	}

	@Override
	@Transactional
	public UserDto signup(SignupDto signupDto) {

		User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);

		if(user != null){
			throw new RuntimeConflictException("Cannot signup, User already exist with email "+signupDto.getEmail());
		}

		User mappedUser = modelMapper.map(signupDto, User.class);
		mappedUser.setRoles(Set.of(Role.RIDER));
		User saveUser = userRepository.save(mappedUser);

		//create user related entities
		Rider rider = riderService.createNewRider(saveUser);

		walletService.createNewWallet(saveUser);


		return modelMapper.map(saveUser, UserDto.class);
	}

	@Override
	public DriverDto onboardNewDriver(Long userId) {
		return null;
	}
}
