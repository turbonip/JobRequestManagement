package com.bangmodteam.workshop.dto;

import java.text.SimpleDateFormat;

import com.bangmodteam.workshop.entity.JobApp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobSearchResultDTO {

	private Long jobId;

	private String promblemCatName;

	private String jobDate;

	private String jobLocationName;

	private String jobDescription;

	private String jobStatusValue;
	private String jobStatusName;

	public JobSearchResultDTO(JobApp jobapp) {

		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");

		if (jobapp != null) {

			this.jobId = jobapp.getId();

			this.promblemCatName = jobapp.getProblemCategory().getName();

			this.jobDate = sdfr.format(jobapp.getOpenAt());

			this.jobLocationName = jobapp.getLocationInfo().getName();

			this.jobDescription = jobapp.getProblemDescription();

			this.jobStatusName = jobapp.getJobStatus().toString();
			this.jobStatusValue = jobapp.getJobStatus().getValue();

		}

	}

}
