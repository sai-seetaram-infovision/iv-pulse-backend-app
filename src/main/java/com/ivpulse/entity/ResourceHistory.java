
package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "resource_history")
@Getter
@Setter
public class ResourceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "ref_type")
    private String refType; // RN, AN

    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @Column(name = "is_billable")
    private boolean isBillable;

    @Column(name = "is_active")
    private boolean isActive;

    private String reason; // optional reason
}
