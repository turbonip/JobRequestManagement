package com.bangmodteam.workshop.dto;

import java.text.SimpleDateFormat;

import com.bangmodteam.workshop.constant.TicketStatus;
import com.bangmodteam.workshop.entity.JobApp;
import com.bangmodteam.workshop.entity.Ticket;
import com.bangmodteam.workshop.utility.DateTimeUtility;

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

		this.ticketId = ticket.getId();

		this.ticketSequence = ticket.getTicketSequence();

		if (ticket.getAssignAt() != null)
			this.assignAt = DateTimeUtility.DateToString(ticket.getAssignAt(), "dd/MM/yyyy");

		if (ticket.getAssignBy() != null)
			this.assignBy = ticket.getAssignBy().getName();

		if (ticket.getAssignTo() != null)
			this.assignTo = ticket.getAssignTo().getName();

		this.assignRemark = ticket.getAssignRemark();

		this.ticketStatus = ticket.getTicketStatus();

		this.ticketStatusValue = ticket.getTicketStatus().getValue();

		this.ticketResovle = ticket.getResovleResult();

		if (ticket.getTakeAt() != null)
			this.takeAt = DateTimeUtility.DateToString(ticket.getTakeAt(), "dd/MM/yyyy");

		if (ticket.getFinishAt() != null)
			this.finishAt = DateTimeUtility.DateToString(ticket.getFinishAt(), "dd/MM/yyyy");

		if (ticket.getVerifyBy() != null)
			this.ticketVerifyBy = ticket.getVerifyBy().getName();

		if (ticket.getVerifyAt() != null)
			this.tickVerifyAt = DateTimeUtility.DateToString(ticket.getVerifyAt(), "dd/MM/yyyy");

	}

	public TicketDetailDTO(JobApp job) {
		super(job, false);
	}
}
