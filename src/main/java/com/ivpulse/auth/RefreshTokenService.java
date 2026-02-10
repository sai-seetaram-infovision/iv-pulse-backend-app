
package com.ivpulse.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	
	@Autowired
	private RefreshTokenRepository repo;

	
	public static record IssueResult(RefreshToken token, String raw) {

		public String raw() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public IssueResult issue(User user, Duration ttl) {
		var id = UUID.randomUUID();
		var raw = id + "." + UUID.randomUUID();
		var hash = BCrypt.hashpw(raw, BCrypt.gensalt());
		var rt = new RefreshToken();
		rt.setId(id);
		rt.setUser(user);
		rt.setTokenHash(hash);
		rt.setExpiresAt(Instant.now().plus(ttl));
		repo.save(rt);
		return new IssueResult(rt, raw);
	}

	public Optional<RefreshToken> findValidById(UUID id) {
		return repo.findByIdAndRevokedFalse(id).filter(t -> t.getExpiresAt().isAfter(Instant.now()));
	}

	public Optional<RefreshToken> findById(UUID id) {
		return repo.findById(id);
	}

	public boolean verify(RefreshToken token, String raw) {
		return !token.isRevoked() && token.getExpiresAt().isAfter(Instant.now())
				&& BCrypt.checkpw(raw, token.getTokenHash());
	}

	public void revoke(RefreshToken token) {
		token.setRevoked(true);
		repo.save(token);
	}
}
