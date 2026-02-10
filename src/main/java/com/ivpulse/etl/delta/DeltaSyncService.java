package com.ivpulse.etl.delta;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivpulse.client.ProductivvClient;
import com.ivpulse.dto.common.DeltaSyncResult;
import com.ivpulse.dto.productivv.EmployeeDto;
import com.ivpulse.dto.productivv.TimesheetDto;
import com.ivpulse.etl.job.EtlJobRun;
import com.ivpulse.etl.job.EtlJobType;
import com.ivpulse.etl.kv.SourceType;
import com.ivpulse.etl.staging.StagingEntity;
import com.ivpulse.etl.support.CursorService;
import com.ivpulse.etl.support.JobRunService;
import com.ivpulse.etl.support.StagingWriterService;
import com.ivpulse.util.DateWindowUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeltaSyncService {
	private static final Logger log = LoggerFactory.getLogger(DeltaSyncService.class);

	private final ProductivvClient client;
	private final StagingWriterService stagingWriter;
	private final CursorService cursorService;
	private final JobRunService jobRunService;
	private final ObjectMapper mapper;

	

	public DeltaSyncService(ProductivvClient client, StagingWriterService stagingWriter, CursorService cursorService,
			JobRunService jobRunService, ObjectMapper mapper) {
		super();
		this.client = client;
		this.stagingWriter = stagingWriter;
		this.cursorService = cursorService;
		this.jobRunService = jobRunService;
		this.mapper = mapper;
	}

	@Transactional
	public DeltaSyncResult runAllDeltas(boolean includeEmployees, boolean includeCurrentMonthTs,
			boolean includeLastMonthTs) {
		long start = System.currentTimeMillis();
		var run = jobRunService.start(EtlJobType.DELTA_SYNC, "Delta sync started");

		int empCount = 0, tsCurCount = 0, tsLastCount = 0, errors = 0;
		LocalDate empFrom = null, empTo = null, curFrom = null, curTo = null, lastFrom = null, lastTo = null;

		try {
			if (includeEmployees) {
				var now = LocalDate.now();
				empFrom = now.minusDays(1);
				empTo = now;
				try {
					empCount = deltaEmployees(empFrom, empTo);
					cursorService.upsert("employees", SourceType.PRODUCTIVV, empFrom, empTo, "Employees delta");
				} catch (Exception e) {
					errors++;
					log.error("Employees delta failed", e);
				}
			}

			if (includeCurrentMonthTs) {
				curFrom = DateWindowUtils.firstDayOfCurrentMonth();
				curTo = LocalDate.now();
				try {
					tsCurCount = deltaTimesheets("timesheets-current", curFrom, curTo);
					cursorService.upsert("timesheets-current", SourceType.PRODUCTIVV, curFrom, curTo,
							"Timesheets current month");
				} catch (Exception e) {
					errors++;
					log.error("Timesheets current month delta failed", e);
				}
			}

			if (includeLastMonthTs) {
				lastFrom = DateWindowUtils.firstDayOfPreviousMonth();
				lastTo = DateWindowUtils.lastDayOfPreviousMonth();
				try {
					tsLastCount = deltaTimesheets("timesheets-last-month", lastFrom, lastTo);
					cursorService.upsert("timesheets-last-month", SourceType.PRODUCTIVV, lastFrom, lastTo,
							"Timesheets last month");
				} catch (Exception e) {
					errors++;
					log.error("Timesheets last month delta failed", e);
				}
			}

			long millis = System.currentTimeMillis() - start;
			jobRunService.success(run, empCount + tsCurCount + tsLastCount, errors, "Delta sync completed");

			DeltaSyncResult result = new DeltaSyncResult();

			result.setEmployeesFrom(empFrom);
			result.setEmployeesTo(empTo);
			result.setEmployeesWritten(empCount);

			result.setTsCurrentFrom(curFrom);
			result.setTsCurrentTo(curTo);
			result.setTimesheetsCurrentWritten(tsCurCount);

			result.setTsLastFrom(lastFrom);
			result.setTsLastTo(lastTo);
			result.setTimesheetsLastWritten(tsLastCount);

			result.setErrors(errors);
			result.setMillis(millis);

			return result;

		} catch (Exception ex) {
			jobRunService.fail(run, ex.getMessage(), errors + 1);
			throw ex;
		}
	}

	private int deltaEmployees(LocalDate from, LocalDate to) throws JsonProcessingException {
		List<EmployeeDto> list = client.getEmployees(from, to);
		int count = 0;
		for (EmployeeDto dto : list) {
			String json = mapper.writeValueAsString(dto);
			stagingWriter.write(StagingEntity.RESOURCE, "resource:" + dto.getId(), json, LocalDate.now());
			count++;
		}
		return count;
	}

	private int deltaTimesheets(String cursorKey, LocalDate from, LocalDate to) throws JsonProcessingException {
		List<TimesheetDto> list = client.getTimesheets(from, to);
		int count = 0;
		for (TimesheetDto dto : list) {
			String json = mapper.writeValueAsString(dto);
			// Prefer upstream Id if present; else compose from keys
			String sid = (dto.getId() != null ? ("timesheet:" + dto.getId())
					: "timesheet:" + dto.getEmployeeId() + ":" + dto.getProjectId() + ":"
							+ (dto.getWorkDate() != null ? dto.getWorkDate().toLocalDate() : from));
			stagingWriter.write(StagingEntity.TIMESHEET_SNAPSHOT, sid, json, LocalDate.now());
			count++;
		}
		return count;
	}

	@Transactional
	public DeltaSyncResult runDeltaSync(boolean includeEmployees, boolean includeCurrentMonthTs,
			boolean includeLastMonthTs) {
		return runAllDeltas(includeEmployees, includeCurrentMonthTs, includeLastMonthTs);
	}

}
