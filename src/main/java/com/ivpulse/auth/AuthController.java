
package com.ivpulse.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthUserRepository users;
	private final JwtService jwt;
	private final RefreshTokenService refreshSvc;
	private final PasswordEncoder encoder;

	public AuthController(AuthUserRepository users, JwtService jwt, RefreshTokenService refreshSvc,
			PasswordEncoder encoder) {
		this.users = users;
		this.jwt = jwt;
		this.refreshSvc = refreshSvc;
		this.encoder = encoder;
	}

	@Value("${app.security.jwt.refresh-token-ttl-days}")
	private long refreshTtlDays;
	@Value("${app.security.cookie.name}")
	private String cookieName;
	@Value("${app.security.cookie.secure}")
	private boolean cookieSecure;
	@Value("${app.security.cookie.domain}")
	private String cookieDomain;

	public record LoginReq(@NotBlank String username, @NotBlank String password) {
	}

	public record LoginRes(String accessToken, String username, java.util.List<String> roles) {
	}

	@PostMapping("/login")
	public ResponseEntity<LoginRes> login(@RequestBody @Valid LoginReq req, HttpServletResponse res) {
		var user = users.findByUsernameAndActiveTrue(req.username())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
		System.out.println("-----------" + encoder.matches(req.password(), user.getPasswordHash()));
		System.out.println("-----------" + user.getUsername() + "---" + user.getPasswordHash());
		if (!encoder.matches(req.password(), user.getPasswordHash()))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

		var access = jwt.generateAccessToken(user);
		var issued = refreshSvc.issue(user, java.time.Duration.ofDays(refreshTtlDays));
		setRefreshCookie(res, issued.raw(), (int) java.time.Duration.ofDays(refreshTtlDays).toSeconds());

		var roles = user.getRoles().stream().map(Role::getRoleName).toList();
		return ResponseEntity.ok(new LoginRes(access, user.getUsername(), roles));
	}

	@PostMapping("/refresh")
	public ResponseEntity<Map<String, String>> refresh(HttpServletRequest req, HttpServletResponse res) {
		String raw = readCookie(req, cookieName);
		var idOpt = extractRefreshId(raw);
		if (idOpt.isEmpty())
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

		var token = refreshSvc.findValidById(idOpt.get())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

		if (!refreshSvc.verify(token, raw)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		// rotate token & cookie
		refreshSvc.revoke(token);
		var user = token.getUser();
		var access = jwt.generateAccessToken(user);
		var issued = refreshSvc.issue(user, Duration.ofDays(refreshTtlDays));
		var cookie = new Cookie(cookieName, issued.raw());
		cookie.setHttpOnly(true);
		cookie.setSecure(cookieSecure);
		cookie.setPath("/");
		cookie.setMaxAge((int) Duration.ofDays(refreshTtlDays).toSeconds());
		cookie.setDomain(cookieDomain);
		res.addCookie(cookie);

		return ResponseEntity.ok(Map.of("accessToken", access));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest req, HttpServletResponse res) {
		String raw = readCookie(req, cookieName);

		// If we can parse a UUID, revoke it; otherwise just proceed to clear cookie
		extractRefreshId(raw).flatMap(refreshSvc::findById).ifPresent(refreshSvc::revoke);

		clearCookie(res);
		return ResponseEntity.noContent().build(); // 204 every time
	}

	@GetMapping("/me")
	public java.util.Map<String, Object> me(Authentication auth) {
		var user = users.findByUsernameAndActiveTrue(auth.getName()).orElseThrow();
		return java.util.Map.of("username", user.getUsername(), "roles",
				user.getRoles().stream().map(Role::getRoleName).toList());
	}

	// helpers
	private String readCookie(HttpServletRequest req, String name) {
		if (req.getCookies() == null)
			return null;
		for (Cookie c : req.getCookies())
			if (c.getName().equals(name))
				return c.getValue();
		return null;
	}

	private void setRefreshCookie(HttpServletResponse res, String value, int maxAgeSeconds) {
		var cookie = new Cookie(cookieName, value);
		cookie.setHttpOnly(true);
		cookie.setSecure(cookieSecure);
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeSeconds);
		cookie.setDomain(cookieDomain);
		res.addCookie(cookie);
	}

	private Optional<UUID> extractRefreshId(String raw) {
		if (raw == null || raw.isBlank())
			return Optional.empty();
		int dot = raw.indexOf('.');
		if (dot <= 0)
			return Optional.empty(); // no id part before '.'
		try {
			return Optional.of(UUID.fromString(raw.substring(0, dot)));
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}

	private void clearCookie(HttpServletResponse res) {
		setRefreshCookie(res, "", 0);
	}
}
