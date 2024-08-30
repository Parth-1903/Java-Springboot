package com.week5_Security.Service;

import com.week5_Security.Dto.SignUpDto;
import com.week5_Security.Dto.UserDto;
import com.week5_Security.Exception.ResourceNotFoundException;
import com.week5_Security.Repository.UserRepository;
import com.week5_Security.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//if you want to use application.properties custom values then remove this @Service
// Then  Global AuthenticationManager configured with InMemoryUserDetailsManager
//If you put @Service there then it will use UserDetailsService bean

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

	public UserDto signUp(SignUpDto signUpDto) {
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if(user.isPresent()){
			throw new BadCredentialsException("User with email already exists "+signUpDto.getEmail());
		}
		User toCreate = modelMapper.map(signUpDto,User.class);
		toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));

		User savedUser = userRepository.save(toCreate);
		return modelMapper.map(savedUser, UserDto.class);
	}

	@Transactional
	public User save(User newUser) {
		return userRepository.save(newUser);
	}
}
