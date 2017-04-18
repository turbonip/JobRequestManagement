package com.bangmodteam.workshop.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bangmodteam.workshop.constant.JobStatus;
import com.bangmodteam.workshop.utility.JobStatusDbConverter;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JobApp extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private ProblemCategory problemCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	private DeviceInfo deviceInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	private ProductLineInfo productLineInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	private LocationInfo locationInfo;

	private String problemDescription;

	//@Enumerated(EnumType.STRING)
	@Convert(converter = JobStatusDbConverter.class)
	private JobStatus jobStatus;

	private String resolveResult;

	@Column(name = "opened_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date openAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "opened_by")
	private User openBy;

	@Column(name = "closed_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date closeAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "closed_by")
	private User closeBy;

	@OneToMany(mappedBy = "jobApp", fetch = FetchType.LAZY)
	private List<Ticket> tickets;

}
