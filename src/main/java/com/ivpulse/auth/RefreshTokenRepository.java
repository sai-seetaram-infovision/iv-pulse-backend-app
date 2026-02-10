
package com.ivpulse.auth;

import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, java.util.UUID> {
    Optional<RefreshToken> findByIdAndRevokedFalse(UUID id);
}
