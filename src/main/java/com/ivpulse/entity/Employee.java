
package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    private String designation;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_billable")
    private boolean isBillable;
}
