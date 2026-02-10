
package com.ivpulse.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final AuthUserRepository userRepo;

	public JwtAuthFilter(JwtService j, AuthUserRepository u) {
		this.jwtService = j;
		this.userRepo = u;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		var auth = req.getHeader("Authorization");
		if (auth != null && auth.startsWith("Bearer ")) {
			try {
				var jws = jwtService.parse(auth.substring(7));
				var userId = Long.parseLong(jws.getBody().getSubject());
				var user = userRepo.findById(userId).orElse(null);
				if (user != null && user.isActive()) {
					var authorities = user.getRoles().stream()
							.map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRoleName()))
							.collect(Collectors.toList());
					var token = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);
					SecurityContextHolder.getContext().setAuthentication(token);
				}
			} catch (Exception e) {
				// ignore invalid token
			}
		}
		chain.doFilter(req, res);
	}
}
