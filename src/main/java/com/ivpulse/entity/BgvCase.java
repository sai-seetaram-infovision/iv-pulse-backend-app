
package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "bgv_case")
@Getter
@Setter
public class BgvCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    private String status; // e.g., In-Progress, Completed

    @Column(name = "started_on")
    private LocalDate startedOn;
}
