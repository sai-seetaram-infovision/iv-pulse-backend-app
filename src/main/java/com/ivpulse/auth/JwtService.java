
package com.ivpulse.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;

@Component
public class JwtService {
	@Value("${app.security.jwt.secret}")
	private String secret;
	@Value("${app.security.jwt.issuer}")
	private String issuer;
	@Value("${app.security.jwt.access-token-ttl-minutes}")
	private long accessTtl;

	public String generateAccessToken(User user) {
		var now = Instant.now();
		var roles = user.getRoles().stream().map(Role::getRoleName).toList();
		return Jwts.builder().setSubject(user.getId().toString()).claim("username", user.getUsername())
				.claim("roles", roles).setIssuer(issuer).setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(Duration.ofMinutes(accessTtl))))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
				.compact();
	}

	public Jws<Claims> parse(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
				.requireIssuer(issuer).build().parseClaimsJws(token);
	}
}
