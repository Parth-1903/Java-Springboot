package com.week6HW.Week_6_Spring_Security_Advanced_HW.service;

import com.week6HW.Week_6_Spring_Security_Advanced_HW.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

	@Value("${jwt.secretKey}")
	private String jwtSecretKey;

	private SecretKey getSecretKey(){
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(User user){
		return Jwts.builder()
				.subject(user.getId().toString())
				.claim("email", user.getEmail())
				.claim("subscription", user.getSubscription().getValue())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+ 1000*60*10))
				.signWith(getSecretKey())
				.compact();
	}

	public String generateRefreshToken(User user){
		return Jwts.builder()
				.subject(user.getId().toString())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+ 1000L *60*60*24*30*6)) // 6 months
				.signWith(getSecretKey())
				.compact();
	}

	public Long getUserIdFromToken(String token){
		Claims claims = Jwts.parser()
				.verifyWith(getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

		return Long.valueOf(claims.getSubject());
	}

	public boolean isTokenExpired(String token){
		LocalDateTime expirationTime = this.extractExpiration(token);
		return expirationTime.isBefore(LocalDateTime.now());
	}

	public LocalDateTime extractExpiration(String token) {
		Claims claims =Jwts.parser()
				.verifyWith(this.getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
		return claims.getExpiration().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}


}
