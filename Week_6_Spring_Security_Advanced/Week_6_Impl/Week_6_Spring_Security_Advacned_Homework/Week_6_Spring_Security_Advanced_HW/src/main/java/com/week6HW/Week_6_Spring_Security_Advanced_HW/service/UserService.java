package com.week6HW.Week_6_Spring_Security_Advanced_HW.service;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.SignUpDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.UserDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.User;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.exception.ResourceNotFoundException;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.orElseThrow(() -> new BadCredentialsException("User with email "+username + " not found!"));
	}

	public User getUserById(Long userId){
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id "+userId+" not found!"));
	}

	public User getUserByEmail(String email){
		return userRepository.findByEmail(email)
				.orElse(null);
	}

	public UserDto signUp(SignUpDto signUpDto){
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if(user.isPresent()){
			throw new BadCredentialsException("User with email already exists "+signUpDto.getEmail());
		}

		User toCreate = modelMapper.map(signUpDto, User.class);
		toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
		toCreate.setMaxLogin(0);
		User savedUser = userRepository.save(toCreate);

		return modelMapper.map(savedUser,UserDto.class);
	}

	public void updateMaxLogin(UserDto userDto){
		User user = modelMapper.map(userDto, User.class);
		user.setMaxLogin(user.getMaxLogin() - 1);
		userRepository.save(user);
	}

	public User save(User newUser){
		return userRepository.save(newUser);
	}

}
