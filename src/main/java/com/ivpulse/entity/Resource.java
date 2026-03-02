
package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "resource")
@Getter
@Setter
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "is_billable")
    private boolean isBillable;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "employee_status_id")
    private Integer employeeStatusId;

    @Column(name = "enabling_unit_id")
    private Long enablingUnitId;
}
