package com.ivpulse.repository;

import com.ivpulse.entity.Client;
import com.ivpulse.entity.enums.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByClientCode(String clientCode);
    List<Client> findByStatus(ClientStatus status);
}
