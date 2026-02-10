package com.ivpulse.dto.productivv;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectRateDto {
	@JsonProperty("Id")
	private Integer id;
	@JsonProperty("ProjectID")
	private Integer projectId;
	@JsonProperty("Title")
	private String title;
	@JsonProperty("GrossContractedOnshoreRate")
	private BigDecimal onshoreRate;
	@JsonProperty("GrossContractedOffshoreRate")
	private BigDecimal offshoreRate;
	@JsonProperty("IsActive")
	private Boolean isActive;
	@JsonProperty("IsDeleted")
	private Boolean isDeleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getOnshoreRate() {
		return onshoreRate;
	}

	public void setOnshoreRate(BigDecimal onshoreRate) {
		this.onshoreRate = onshoreRate;
	}

	public BigDecimal getOffshoreRate() {
		return offshoreRate;
	}

	public void setOffshoreRate(BigDecimal offshoreRate) {
		this.offshoreRate = offshoreRate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
