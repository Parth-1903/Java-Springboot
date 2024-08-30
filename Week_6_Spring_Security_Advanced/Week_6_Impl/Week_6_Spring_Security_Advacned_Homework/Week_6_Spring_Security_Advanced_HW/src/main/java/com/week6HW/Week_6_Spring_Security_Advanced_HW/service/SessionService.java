package com.week6HW.Week_6_Spring_Security_Advanced_HW.service;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.dto.SessionDto;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.Session;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.User;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.exception.ResourceNotFoundException;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.exception.SessionLimitException;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.repository.SessionRepository;
import com.week6HW.Week_6_Spring_Security_Advanced_HW.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

	private final SessionRepository sessionRepository;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;


	@Transactional
	public void generateNewSession(User user, String refreshToken){
		List<Session> userSessions = sessionRepository.findByUser(user);

		if(!userSessions.isEmpty() && userSessions.size() == user.getSubscription().getCount()){

			// You can use any of them like either throw an error or remove the first session like queue


//			throw new SessionLimitException("Session Limit exceed!!");

			userSessions.sort(Comparator.comparing(Session::getCreatedAt));
			Session firstUsedSession = userSessions.stream().findFirst().orElse(null);

			if(Objects.nonNull(firstUsedSession)){
				User sessionUser = firstUsedSession.getUser();
				sessionRepository.delete(firstUsedSession);
				sessionUser.setMaxLogin(sessionUser.getMaxLogin()-1);
				userRepository.save(sessionUser);
			}
		}

		Session session = userSessions.stream().filter(f ->
				f.getRefreshToken().equals(refreshToken)
				).findFirst().orElse(null);

		if(Objects.nonNull(session)){
			if(session.getExpiredDate().isBefore(LocalDateTime.now())){
				sessionRepository.deleteById(session.getId());
				throw new BadCredentialsException("Session Already expired!! try to login");
			}
			else{
				session.setRefreshToken(refreshToken);
				session.setExpiredDate(jwtService.extractExpiration(refreshToken));
				sessionRepository.save(session);
				return;
			}

		}

		Session newSession = Session.builder()
				.user(user)
				.refreshToken(refreshToken)
				.expiredDate(jwtService.extractExpiration(refreshToken))
				.build();

		sessionRepository.save(newSession);

		user.setMaxLogin(user.getMaxLogin()+1);
		userRepository.save(user);

	}


	@Transactional
	public boolean deleteByToken(String refreshToken){
		if(sessionRepository.existsByRefreshToken(refreshToken)){
			sessionRepository.deleteByRefreshToken(refreshToken);
			return true;
		}
		throw new ResourceNotFoundException("Already Logout!!");
	}


	public SessionDto getSessionFromRefreshToken(String refreshToken) {
		Optional<Session> session = sessionRepository.findByRefreshToken(refreshToken);
		if(session.isEmpty()){
			throw new ResourceNotFoundException("Session doesn't exists!");
		}
		return modelMapper.map(session.get(), SessionDto.class);
	}
}
