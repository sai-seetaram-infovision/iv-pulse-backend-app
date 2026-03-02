package com.ivpulse.client;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.ivpulse.dto.productivv.BillingSnapshotDto;
import com.ivpulse.dto.productivv.CostCenterDto;
import com.ivpulse.dto.productivv.Designation;
import com.ivpulse.dto.productivv.EmployeeDto;
import com.ivpulse.dto.productivv.HiringDto;
import com.ivpulse.dto.productivv.LeaveType;
import com.ivpulse.dto.productivv.OnboardingDto;
import com.ivpulse.dto.productivv.ProjectDto;
import com.ivpulse.dto.productivv.ProjectRateDto;
import com.ivpulse.dto.productivv.ResourceAssignmentDto;
import com.ivpulse.dto.productivv.ResourceHistory;
import com.ivpulse.dto.productivv.RoleRefDto;
import com.ivpulse.dto.productivv.TimesheetDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductivvClientImpl implements ProductivvClient {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(ProductivvClientImpl.class);

	private final WebClient webClient;
	private final ProductivvProperties props;

	public ProductivvClientImpl(@Qualifier("productivvWebClient") WebClient productivvWebClient,
			ProductivvProperties props) {
		this.webClient = productivvWebClient;
		this.props = props;
	}

	@Override
	public List<CostCenterDto> getCostCenters() {
		return getList("/costcenters", CostCenterDto[].class);
	}

	@Override
	public List<ProjectDto> getProjects() {
		return getList("/projects", ProjectDto[].class);
	}

	@Override
	public List<RoleRefDto> getRolesReference() {
		// Source uses /reference (roles)
		return getList("/role", RoleRefDto[].class);
	}

	@Override
	public List<ProjectRateDto> getProjectRates() {
		return getList("/projectrate", ProjectRateDto[].class);
	}

	@Override
	public List<EmployeeDto> getEmployees() {
		return getList("/employees", EmployeeDto[].class);
	}

	@Override
	public List<EmployeeDto> getEmployees(LocalDate from, LocalDate to) {
		String path = buildRangePath("/employees", from, to);
		return getList(path, EmployeeDto[].class);
	}

	@Override
	public List<ResourceAssignmentDto> getResources() {
		return getList("/resources", ResourceAssignmentDto[].class);
	}

	@Override
	public List<HiringDto> getHiring() {
		return getList("/hiring", HiringDto[].class);
	}

	@Override
	public List<OnboardingDto> getOnboarding() {
		return getList("/Onboarding", OnboardingDto[].class);
	}

	@Override
	public List<BillingSnapshotDto> getBillingSnapshots() {
//		return getList("/billing-snapshot", BillingSnapshotDto[].class);
		return null;
	}

	@Override
	public List<TimesheetDto> getTimesheets(LocalDate from, LocalDate to) {
		String path = buildRangePath("/timesheets", from, to);
		return getList(path, TimesheetDto[].class);
	}

	private String buildRangePath(String base, LocalDate from, LocalDate to) {
		StringBuilder sb = new StringBuilder(base);
		if (from != null && to != null) {
			sb.append("?from=").append(from).append("&to=").append(to);
		} else if (from != null) {
			sb.append("?from=").append(from);
		} else if (to != null) {
			sb.append("?to=").append(to);
		}
		return sb.toString();
	}

	private <T> List<T> getList(String path, Class<T[]> type) {
		T[] body = webClient.get().uri(path).retrieve()
				.onStatus(HttpStatusCode::isError,
						resp -> resp.bodyToMono(String.class).defaultIfEmpty("Productivv error")
								.flatMap(msg -> Mono.error(new ProductivvApiException(
										"HTTP " + resp.statusCode().value() + ": " + msg, resp.statusCode().value()))))
				.bodyToMono(type).doOnSubscribe(s -> log.debug("Calling Productivv GET {}", path))
				.doOnSuccess(res -> log.debug("Productivv GET {} -> {} elements", path, res == null ? 0 : res.length))
				.onErrorMap(ex -> (ex instanceof ProductivvApiException) ? ex
						: new ProductivvApiException("Call failed for " + path + ": " + ex.getMessage(), 500))
				.block();

		return (body == null) ? List.of() : List.of(body); // since Java 9, returns immutable list copying array
	}

	@Override
	public List<ResourceHistory> getResourceHistoy() {
		return getList("/ResourceHistory", ResourceHistory[].class);
	}

	@Override
	public List<LeaveType> getLeaveTypes() {
		return getList("/LeaveType", LeaveType[].class);
	}

	@Override
	public List<Designation> getDesignations() {
		return getList("/Designation", Designation[].class);
	}

}
