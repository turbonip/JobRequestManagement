package com.bangmodteam.workshop.utility;

import javax.persistence.AttributeConverter;
import com.bangmodteam.workshop.constant.JobStatus;
import com.bangmodteam.workshop.constant.TicketStatus;

public class JobStatusDbConverter implements AttributeConverter<JobStatus, String> {

	@Override
	public String convertToDatabaseColumn(JobStatus jobStatus) {
		
		return jobStatus.getValue();
		
	}

	@Override
	public JobStatus convertToEntityAttribute(String dbData) {
		
		JobStatus status = JobStatus.UNDIFIEND;
		
		if (dbData != null && !dbData.trim().isEmpty()) {

			switch (dbData.toUpperCase()) {

			case "O":
				status = JobStatus.OPEN;
				break;

			case "P":
				status = JobStatus.PROCESS;
				break;

			case "C":
				status = JobStatus.CLOSE;
				break;

			default:
				break;

			}

		}
		
		return status;
		
	}

}
