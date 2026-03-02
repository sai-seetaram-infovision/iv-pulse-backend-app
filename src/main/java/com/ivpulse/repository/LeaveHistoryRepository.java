
package com.ivpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ivpulse.entity.LeaveHistory;

import java.util.List;

public interface LeaveHistoryRepository extends JpaRepository<LeaveHistory, Long> {

    @Query(value = """
        SELECT lh.employee_id, lh.leave_name, lh.start_date, lh.to_date
        FROM leave_history lh
        JOIN leave_year ly ON lh.instance_id = ly.instance_id
        WHERE lh.leave_name = 'Maternity Leave'
          AND lh.leave_status = 'approved'
          AND (ly.ext_id = ?1 OR CAST(ly.id AS TEXT) = ?1)
        ORDER BY lh.employee_id, lh.start_date
    """, nativeQuery = true)
    List<Object[]> findApprovedMaternityByLeaveYear(String leaveYearIdOrGuid);
}
