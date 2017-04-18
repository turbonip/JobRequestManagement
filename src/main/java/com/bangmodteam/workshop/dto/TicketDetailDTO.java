package com.bangmodteam.workshop.dto;

import java.text.SimpleDateFormat;

import com.bangmodteam.workshop.constant.TicketStatus;
import com.bangmodteam.workshop.entity.JobApp;
import com.bangmodteam.workshop.entity.Ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TicketDetailDTO extends JobDetailDTO {

	private Long ticketId;

	private Integer ticketSequence;

	private String assignAt;
	private String assignAtStart;
	private String assignAtEnd;

	private String assignBy;

	private Long assignToId;
	private String assignTo;

	private String assignRemark;

	private String takeAt;

	private String finishAt;

	private String ticketVerifyBy;

	private String tickVerifyAt;

	private TicketStatus ticketStatus;

	private String ticketStatusValue;

	private String ticketResovle;

	public TicketDetailDTO(Ticket ticket) {

		super(ticket.getJobApp(), false);

		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");

		this.ticketId = ticket.getId();

		this.ticketSequence = ticket.getTicketSequence();

		if (ticket.getAssignAt() != null)
			this.assignAt = sdfr.format(ticket.getAssignAt());

		if (ticket.getAssignBy() != null)
			this.assignBy = ticket.getAssignBy().getUsername();

		if (ticket.getAssignTo() != null)
			this.assignTo = ticket.getAssignTo().getUsername();

		this.assignRemark = ticket.getAssignRemark();

		this.ticketStatus = ticket.getTicketStatus();

		this.ticketStatusValue = ticket.getTicketStatus().getValue();

		this.ticketResovle = ticket.getResovleResult();

		if (ticket.getTakeAt() != null)
			this.takeAt = sdfr.format(ticket.getTakeAt());

		if (ticket.getFinishAt() != null)
			this.finishAt = sdfr.format(ticket.getFinishAt());

		if (ticket.getVerifyBy() != null)
			this.ticketVerifyBy = ticket.getVerifyBy().getUsername();

		if (ticket.getVerifyAt() != null)
			this.tickVerifyAt = sdfr.format(ticket.getVerifyAt());

	}

	public TicketDetailDTO(JobApp job) {
		super(job, false);
	}
}
