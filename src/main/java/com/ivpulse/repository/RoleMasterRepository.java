package com.ivpulse.repository;

import com.ivpulse.entity.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.UUID;

@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMaster, UUID> {
    Optional<RoleMaster> findByRoleName(String roleName);
}
