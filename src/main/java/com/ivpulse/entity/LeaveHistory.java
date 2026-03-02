
package com.ivpulse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "leave_history")
@Getter
@Setter
public class LeaveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "leave_name")
    private String leaveName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "instance_id")
    private String instanceId;

    @Column(name = "leave_status")
    private String leaveStatus;
}
