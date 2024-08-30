package com.week5_homework.services;

import com.week5_homework.dto.SignUpDto;
import com.week5_homework.dto.UserDto;
import com.week5_homework.entities.User;
import com.week5_homework.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
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
	private final SessionService sessionService;
	private final PasswordEncoder passwordEncoder;

	public UserDto getUserDtoFromUser(User user){
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.orElseThrow(() -> new BadCredentialsException("User with email "+username+ " not found!"));
	}

	public User getUserById(Long userId){
		return userRepository.findById(userId)
				.orElseThrow(() -> new BadCredentialsException("User with id "+userId+" not found!"));
	}

	public UserDto signUp(SignUpDto signUpDto){
		Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());
		if(user.isPresent()){
			throw new BadCredentialsException("User with email already exists "+signUpDto.getEmail());
		}

		User toCreate = modelMapper.map(signUpDto, User.class);
		toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));

		User savedUser = userRepository.save(toCreate);

		return modelMapper.map(savedUser, UserDto.class);
	}

	public UserDto getProfileById(Long id) {

		User user = this.getUserById(id);

		String token = user.getSession().getToken() != null ? user.getSession().getToken() : null;

		if(!sessionService.getValidSession(user,token)){
			throw new CredentialsExpiredException("Session already expired!");
		}

		return modelMapper.map(user,UserDto.class);
	}
}
