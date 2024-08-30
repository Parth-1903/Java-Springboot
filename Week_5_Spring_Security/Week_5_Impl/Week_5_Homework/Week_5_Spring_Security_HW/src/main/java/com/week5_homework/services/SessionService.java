package com.week5_homework.services;

import com.week5_homework.dto.SessionDto;
import com.week5_homework.entities.Session;
import com.week5_homework.entities.User;
import com.week5_homework.repositories.SessionRepository;
import com.week5_homework.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

	private final UserRepository userRepository;
	private final SessionRepository sessionRepository;
	private final JwtService jwtService;
	private final ModelMapper modelMapper;

	@Transactional
	public boolean getValidSession(User user, String token){
//		if(user != null) {
			Optional<Session> session = sessionRepository.findByUser(user);
			if (session.isPresent()) {
				//Check if existing token has expired
//			boolean isExpired = jwtService.isTokenExpired(session.get().getToken());
				boolean isExpired = this.checkTokenExpired(session.get());
				if (isExpired) {
					log.info("Session expired for user: {}", user.getId());

					//handle expired session, create new session
					sessionRepository.delete(session.get());

					user.setSession(null);
					userRepository.save(user);
					return false;
				} else {
					//return the existing valid session
					Session s = session.get();
					s.setToken(token);
					s.setExpiredAt(jwtService.extractExpiration(token));
					modelMapper.map(sessionRepository.save(s), SessionDto.class);
					return true;
				}
			}

			//create new session if no session is found
			Session newSession = new Session();
			newSession.setUser(user);
			newSession.setToken(token);
			//During creation we are taking expiration token
			newSession.setExpiredAt(jwtService.extractExpiration(token));

			user.setSession(newSession);
			userRepository.save(user);
			modelMapper.map(newSession, SessionDto.class);
			return true;
		}
//		else{
//		return false;
//	}

	private boolean checkTokenExpired(Session session) {
		LocalDateTime expiredTime = session.getExpiredAt();
		return expiredTime.isBefore(LocalDateTime.now());
	}

	public Optional<Session> getValidSession(String token){
		return sessionRepository.findByToken(token);
	}

}
