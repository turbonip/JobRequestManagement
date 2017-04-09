package com.bangmodteam.workshop.entity;

import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bangmodteam.workshop.constant.TicketStatus;
import com.bangmodteam.workshop.utility.TicketStatusDbConverter;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticket extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	private JobApp jobApp;

	private Integer ticketSequence;

	@ManyToOne(fetch = FetchType.LAZY)
	private User assignBy;

	@ManyToOne(fetch = FetchType.LAZY)
	private User assignTo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date assignAt;

	@Convert(converter = TicketStatusDbConverter.class)
	private TicketStatus ticketStatus;

	private String resovleResult;

	@Temporal(TemporalType.TIMESTAMP)
	private Date takeAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date finishAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private User verifyBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date verifyAt;

	@PrePersist
	protected void onCreate() {
		super.onCreate();

		// TODO: find sequence number for ticketSequence

	}

}
