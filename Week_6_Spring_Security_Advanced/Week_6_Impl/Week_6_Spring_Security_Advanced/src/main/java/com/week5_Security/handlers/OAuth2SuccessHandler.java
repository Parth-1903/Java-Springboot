package com.week5_Security.handlers;

import com.week5_Security.Service.JwtService;
import com.week5_Security.Service.UserService;
import com.week5_Security.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final UserService userService;
	private final JwtService jwtService;

	@Value("${deploy.env}")
	private String deployEnv;


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

//		log.info(oAuth2User.getAttribute("email"));

		String email = oAuth2User.getAttribute("email");

		User user = userService.getUserByEmail(email);

		if(user == null){
			User newUser = User.builder()
					.name(oAuth2User.getAttribute("name"))
					.email(email)
					.build();
			user = userService.save(newUser);
		}

		String accessToken = jwtService.generateAccessToken(user);
		String refreshToken = jwtService.generateRefreshToken(user);


		Cookie cookie = new Cookie("refreshToken",refreshToken);
		cookie.setHttpOnly(true); //It is accessed by HTTP not JS.
		cookie.setSecure("production".equals(deployEnv)); //It  is access by only Secure HTTPS only.
		response.addCookie(cookie);

		String frontEndUrl = "http://localhost:8099/home.html?token="+accessToken;

		getRedirectStrategy().sendRedirect(request, response, frontEndUrl);
	}

}
