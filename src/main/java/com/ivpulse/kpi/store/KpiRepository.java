
package com.ivpulse.kpi.store;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;

@Repository
public class KpiRepository {

    private final JdbcTemplate jdbc;

    public KpiRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    /**
     * Available hours = sum(plan_hours) from engagement_resource for resources active in month
     * If you don't store plan_hours, fallback to 8h * working_days count derived from calendar table (if present)
     * or simply 160h per FTE for PoC. Here we assume a plan_hours column; otherwise adjust to your model.
     */
    public double fetchAvailableHours(YearMonth ym) {
        String sql = """
            SELECT COALESCE(SUM(er.plan_hours), 0) AS available
            FROM engagement_resource er
            WHERE (er.start_date IS NULL OR er.start_date <= ?)
              AND (er.end_date   IS NULL OR er.end_date   >= ?)
        """;
        return jdbc.queryForObject(sql, Double.class, ym.atEndOfMonth(), ym.atDay(1));
    }

    /**
     * Billable hours = sum(approved or submitted) from timesheet_snapshot for that month.
     */
    public double fetchBillableHours(YearMonth ym) {
        String sql = """
            SELECT COALESCE(SUM(ts.total_hours), 0) AS billable
            FROM timesheet_snapshot ts
            WHERE ts.year_month = ?
              AND (ts.submitted_flag = TRUE OR ts.approved_flag = TRUE)
        """;
        return jdbc.queryForObject(sql, Double.class, ym.toString());
    }

    /**
     * Unbilled hours = snapshot hours that do not have a billing record for that month.
     * We consider an entry unbilled when there is no matching billing_snapshot by (resource, engagement, month)
     * or the billed_amount = 0.
     */
    public double fetchUnbilledHours(YearMonth ym) {
        String sql = """
            SELECT COALESCE(SUM(ts.total_hours), 0) AS unbilled_hours
            FROM timesheet_snapshot ts
            LEFT JOIN billing_snapshot b
              ON b.engagement_id = ts.engagement_id
             AND b.engagement_resource_id = ts.engagement_resource_id
             AND b.year_month = ts.year_month
            WHERE ts.year_month = ?
              AND (ts.submitted_flag = TRUE OR ts.approved_flag = TRUE)
              AND (b.billed_amount IS NULL OR b.billed_amount = 0)
        """;
        return jdbc.queryForObject(sql, Double.class, ym.toString());
    }

    /**
     * A simple blended rate: avg(rate) from active rate_card rows.
     * If your schema stores rate per engagement_resource, adjust join accordingly.
     */
    public double fetchBlendedRate() {
        String sql = "SELECT COALESCE(AVG(rc.rate_amount), 0) FROM rate_card rc";
        Double val = jdbc.queryForObject(sql, Double.class);
        return val == null ? 0.0 : val;
    }
}
