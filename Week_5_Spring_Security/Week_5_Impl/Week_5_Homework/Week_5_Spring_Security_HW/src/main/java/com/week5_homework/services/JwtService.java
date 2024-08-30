package com.week5_homework.services;

import com.week5_homework.entities.User;
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
import java.util.Set;

@Service
public class JwtService {

	@Value("${jwt.secretKey}")
	private String jwtSecretKey;

	private SecretKey getSecretKey(){
		return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(User user){
		return Jwts.builder()
				.subject(user.getId().toString())
				.claim("email",user.getEmail())
				.claim("roles", Set.of("ADMIN","USER"))
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000*60*5)) // This token is valid till 5 minutes
				.signWith(this.getSecretKey())
				.compact();
	}

	public Long getUserIdFromToken(String token){
		Claims claims = Jwts.parser()
				.verifyWith(this.getSecretKey())
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
		Claims claims = Jwts.parser()
				.verifyWith(this.getSecretKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();

		return claims.getExpiration().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
	}
}
