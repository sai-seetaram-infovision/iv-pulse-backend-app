
package com.ivpulse.auth;

import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface AuthUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndActiveTrue(String username);
}
