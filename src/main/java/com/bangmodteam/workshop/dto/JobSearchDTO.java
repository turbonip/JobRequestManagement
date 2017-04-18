package com.bangmodteam.workshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobSearchDTO {

	private Long jobId;

	private Long problemCat;

	private String jobDateBegin;

	private String jobDateEnd;

	private Long jobLocation;

	private String jobStatus;

}
