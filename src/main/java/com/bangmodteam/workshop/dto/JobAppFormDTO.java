package com.bangmodteam.workshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobAppFormDTO {

	private Long jobId;
	
	private Long locationId;
	
	private Long productLineId;
	
	private Long problemCatId;
	
	private Long deviceInfoId;
	
	private String jobDescription;
	
	private String jobStatus;
	
	private String jobResolve;	
	
}
