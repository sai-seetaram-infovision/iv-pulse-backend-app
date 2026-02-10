package com.ivpulse.kpi.store;

import com.ivpulse.kpi.dto.KpiFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilterAwareKpiRepository {

    private final JdbcTemplate jdbc;

    public FilterAwareKpiRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /* =======================
       AVAILABLE HOURS
       ======================= */

    public double fetchAvailableHours(YearMonth ym, KpiFilter f) {
        StringBuilder sql = new StringBuilder("""
            SELECT COALESCE(SUM(er.plan_hours), 0)
            FROM engagement_resource er
            JOIN engagement e ON e.engagement_id = er.engagement_id
            JOIN client c     ON c.client_id = e.client_id
            LEFT JOIN role_master r ON r.role_id = er.role_id
            WHERE (er.start_date IS NULL OR er.start_date <= ?)
              AND (er.end_date   IS NULL OR er.end_date   >= ?)
        """);

        List<Object> args = new ArrayList<>();
        args.add(ym.atEndOfMonth());
        args.add(ym.atDay(1));

        applyFilters(sql, args, f);

        return jdbc.queryForObject(sql.toString(), Double.class, args.toArray());
    }

    /* =======================
       BILLABLE HOURS
       ======================= */

    public double fetchBillableHours(YearMonth ym, KpiFilter f) {
        StringBuilder sql = new StringBuilder("""
            SELECT COALESCE(SUM(ts.total_hours), 0)
            FROM timesheet_snapshot ts
            JOIN engagement_resource er ON er.engagement_resource_id = ts.engagement_resource_id
            JOIN engagement e ON e.engagement_id = ts.engagement_id
            JOIN client c     ON c.client_id = e.client_id
            LEFT JOIN role_master r ON r.role_id = er.role_id
            WHERE ts.year_month = ?
              AND (ts.submitted_flag = TRUE OR ts.approved_flag = TRUE)
        """);

        List<Object> args = new ArrayList<>();
        args.add(ym.toString());

        applyFilters(sql, args, f);

        return jdbc.queryForObject(sql.toString(), Double.class, args.toArray());
    }

    /* =======================
       UNBILLED HOURS
       ======================= */

    public double fetchUnbilledHours(YearMonth ym, KpiFilter f) {
        StringBuilder sql = new StringBuilder("""
            SELECT COALESCE(SUM(ts.total_hours), 0)
            FROM timesheet_snapshot ts
            JOIN engagement_resource er ON er.engagement_resource_id = ts.engagement_resource_id
            JOIN engagement e ON e.engagement_id = ts.engagement_id
            JOIN client c     ON c.client_id = e.client_id
            LEFT JOIN billing_snapshot b
              ON b.engagement_id = ts.engagement_id
             AND b.engagement_resource_id = ts.engagement_resource_id
             AND b.year_month = ts.year_month
            LEFT JOIN role_master r ON r.role_id = er.role_id
            WHERE ts.year_month = ?
              AND (ts.submitted_flag = TRUE OR ts.approved_flag = TRUE)
              AND (b.billed_amount IS NULL OR b.billed_amount = 0)
        """);

        List<Object> args = new ArrayList<>();
        args.add(ym.toString());

        applyFilters(sql, args, f);

        return jdbc.queryForObject(sql.toString(), Double.class, args.toArray());
    }

    /* =======================
       UNBILLED BREAKDOWN
       ======================= */

    public List<Object[]> fetchUnbilledBreakdown(
            YearMonth ym,
            KpiFilter f,
            int page,
            int size,
            String sortCol,
            String sortDir
    ) {

        String sortColumn = switch (sortCol == null ? "amount" : sortCol.toLowerCase()) {
            case "hours" -> "hours";
            case "rate"  -> "rate";
            default      -> "amount";
        };

        String sortDirection =
                "ASC".equalsIgnoreCase(sortDir) ? "ASC" : "DESC";

        StringBuilder sql = new StringBuilder("""
            WITH base AS (
                SELECT ts.year_month,
                       c.client_id, c.client_name,
                       e.engagement_id, e.engagement_name,
                       er.resource_id, res.employee_name AS resource_name,
                       er.role_id, rm.role_name,
                       er.location,
                       SUM(ts.total_hours) AS hours
                FROM timesheet_snapshot ts
                JOIN engagement_resource er ON er.engagement_resource_id = ts.engagement_resource_id
                JOIN engagement e ON e.engagement_id = ts.engagement_id
                JOIN client c     ON c.client_id = e.client_id
                JOIN resource res ON res.resource_id = er.resource_id
                LEFT JOIN role_master rm ON rm.role_id = er.role_id
                LEFT JOIN billing_snapshot b
                  ON b.engagement_id = ts.engagement_id
                 AND b.engagement_resource_id = ts.engagement_resource_id
                 AND b.year_month = ts.year_month
                WHERE ts.year_month = ?
                  AND (ts.submitted_flag = TRUE OR ts.approved_flag = TRUE)
                  AND (b.billed_amount IS NULL OR b.billed_amount = 0)
        """);

        List<Object> args = new ArrayList<>();
        args.add(ym.toString());

        applyFilters(sql, args, f);

        sql.append("""
                GROUP BY ts.year_month,
                         c.client_id, c.client_name,
                         e.engagement_id, e.engagement_name,
                         er.resource_id, res.employee_name,
                         er.role_id, rm.role_name, er.location
            ), rated AS (
                SELECT base.*,
                       COALESCE(rc.rate_amount, avg_rate.avg_rate) AS rate
                FROM base
                LEFT JOIN rate_card rc
                  ON rc.engagement_id = base.engagement_id
                 AND (rc.role_id = base.role_id OR base.role_id IS NULL)
                CROSS JOIN (
                    SELECT COALESCE(AVG(rate_amount), 0) AS avg_rate
                    FROM rate_card
                ) avg_rate
            )
            SELECT year_month,
                   client_id, client_name,
                   engagement_id, engagement_name,
                   resource_id, resource_name,
                   role_id, role_name,
                   location,
                   hours,
                   rate,
                   (hours * rate) AS amount
            FROM rated
            ORDER BY """ + sortColumn + " " + sortDirection + """
            LIMIT ? OFFSET ?
        """);

        args.add(size);
        args.add(page * size);

        return jdbc.query(
                sql.toString(),
                (rs, i) -> new Object[]{
                        rs.getString("year_month"),
                        rs.getString("client_id"),
                        rs.getString("client_name"),
                        rs.getString("engagement_id"),
                        rs.getString("engagement_name"),
                        rs.getString("resource_id"),
                        rs.getString("resource_name"),
                        rs.getString("role_id"),
                        rs.getString("role_name"),
                        rs.getString("location"),
                        rs.getDouble("hours"),
                        rs.getDouble("rate"),
                        rs.getDouble("amount")
                },
                args.toArray()
        );
    }

    /* =======================
       UNBILLED COUNT
       ======================= */

    public long countUnbilledBreakdown(YearMonth ym, KpiFilter f) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(1) FROM (
                SELECT 1
                FROM timesheet_snapshot ts
                JOIN engagement_resource er ON er.engagement_resource_id = ts.engagement_resource_id
                JOIN engagement e ON e.engagement_id = ts.engagement_id
                JOIN client c     ON c.client_id = e.client_id
                LEFT JOIN billing_snapshot b
                  ON b.engagement_id = ts.engagement_id
                 AND b.engagement_resource_id = ts.engagement_resource_id
                 AND b.year_month = ts.year_month
                WHERE ts.year_month = ?
                  AND (ts.submitted_flag = TRUE OR ts.approved_flag = TRUE)
                  AND (b.billed_amount IS NULL OR b.billed_amount = 0)
        """);

        List<Object> args = new ArrayList<>();
        args.add(ym.toString());

        applyFilters(sql, args, f);

        sql.append("""
                GROUP BY ts.year_month, e.engagement_id, er.resource_id
            ) x
        """);

        return jdbc.queryForObject(sql.toString(), Long.class, args.toArray());
    }

    /* =======================
       COMMON FILTERS
       ======================= */

    private void applyFilters(StringBuilder sql, List<Object> args, KpiFilter f) {
        if (f == null) return;

        if (f.clientId() != null && !f.clientId().isBlank()) {
            sql.append(" AND c.client_id = ?");
            args.add(f.clientId());
        }

        if (f.engagementId() != null && !f.engagementId().isBlank()) {
            sql.append(" AND e.engagement_id = ?");
            args.add(f.engagementId());
        }

        if (f.roleId() != null && !f.roleId().isBlank()) {
            sql.append(" AND er.role_id = ?");
            args.add(f.roleId());
        }

        if (f.location() != null && !f.location().isBlank()) {
            sql.append(" AND COALESCE(er.location, '') = ?");
            args.add(f.location());
        }
    }
}
