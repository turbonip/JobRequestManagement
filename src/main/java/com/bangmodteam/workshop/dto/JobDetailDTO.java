package com.bangmodteam.workshop.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.bangmodteam.workshop.constant.JobStatus;
import com.bangmodteam.workshop.entity.JobApp;
import com.bangmodteam.workshop.utility.DateTimeUtility;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobDetailDTO {

	private Long jobId;

	private String jobOpenDate;

	private String jobOpenBy;

	private String jobCloseDate;

	private String jobCloseBy;

	private Long promblemCatId;
	private String promblemCatName;

	private Long jobLocationId;
	private String jobLocationName;

	private String productLineName;

	private Long deviceInfoId;
	private String deviceInfoName;

	private String jobDescription;

	private JobStatus jobStatus;

	private String jobStatusName;

	private String jobResolve;

	private List<TicketDetailDTO> tickets;

	public JobDetailDTO(JobApp jobapp, boolean includeTickets) {

		this.tickets = new ArrayList<TicketDetailDTO>();

		if (jobapp != null) {

			this.jobId = jobapp.getId();

			this.jobOpenDate = DateTimeUtility.DateToString(jobapp.getOpenAt(), "dd/MM/yyyy");

			this.jobOpenBy = jobapp.getOpenBy().getName();

			if ((jobapp.getCloseAt() != null))
				this.jobCloseDate = DateTimeUtility.DateToString(jobapp.getCloseAt(), "dd/MM/yyyy");

			if (jobapp.getCloseBy() != null)
				this.jobCloseBy = jobapp.getCloseBy().getName();

			this.promblemCatName = jobapp.getProblemCategory().getName();

			this.jobLocationName = jobapp.getLocationInfo().getName();

			this.productLineName = jobapp.getProductLineInfo().getLineName();

			this.deviceInfoName = jobapp.getDeviceInfo().getName();

			this.jobDescription = jobapp.getProblemDescription();

			this.jobStatus = jobapp.getJobStatus();

			this.jobStatusName = jobapp.getJobStatus().toString();

			this.jobResolve = jobapp.getResolveResult();

			if (includeTickets && jobapp.getTickets() != null) {
				jobapp.getTickets().forEach(ticket -> this.tickets.add(new TicketDetailDTO(ticket)));
			}

		}

	}

}
