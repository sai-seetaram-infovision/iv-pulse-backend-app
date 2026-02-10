package com.ivpulse.repository;

import com.ivpulse.entity.RateCard;
import com.ivpulse.entity.Engagement;
import com.ivpulse.entity.RoleMaster;
import com.ivpulse.entity.enums.RateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.UUID;

@Repository
public interface RateCardRepository extends JpaRepository<RateCard, UUID> {
    List<RateCard> findByEngagement(Engagement engagement);
    List<RateCard> findByEngagement_EngagementId(UUID engagementId);
    List<RateCard> findByEngagementAndRole(Engagement engagement, RoleMaster role);
    List<RateCard> findByRateType(RateType type);
}
