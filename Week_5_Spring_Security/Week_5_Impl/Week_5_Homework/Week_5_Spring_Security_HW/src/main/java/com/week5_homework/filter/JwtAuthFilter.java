package com.week5_homework.filter;

import com.week5_homework.entities.Session;
import com.week5_homework.entities.User;
import com.week5_homework.repositories.SessionRepository;
import com.week5_homework.repositories.UserRepository;
import com.week5_homework.services.JwtService;
import com.week5_homework.services.SessionService;
import com.week5_homework.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
	private final SessionRepository sessionRepository;

	private final JwtService jwtService;
	private final UserService userService;
	private final SessionService sessionService;
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver handlerExceptionResolver;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try{
			log.error("going into JwtAuthFilter");
			final String requestTokenHeader = request.getHeader("Authorization");
			if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
				filterChain.doFilter(request,response);
				return;
			}

			String token = requestTokenHeader.split("Bearer ")[1];
			//==================== This section will delete token if it is expired token in SessionEntity
			// We can do this in LogFilter as well
			Optional<Session> optionalSession = sessionService.getValidSession(token);

			if (optionalSession.isEmpty() || !sessionService.getValidSession(optionalSession.get().getUser(), token)) {
				throw new CredentialsExpiredException("Session already expired!");
			}

			//===============================================================================

			Long userId = jwtService.getUserIdFromToken(token);

			if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
				User user = userService.getUserById(userId);

				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(user,null,null);
				authenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
				);

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			filterChain.doFilter(request,response);
		} catch (Exception e){
			handlerExceptionResolver.resolveException(request,response,null,e);
		}
	}
}
