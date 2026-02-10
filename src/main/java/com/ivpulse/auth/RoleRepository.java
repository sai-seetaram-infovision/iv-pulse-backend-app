
package com.ivpulse.auth;

import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);
}
